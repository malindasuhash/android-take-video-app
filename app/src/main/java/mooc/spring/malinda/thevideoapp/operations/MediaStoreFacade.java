package mooc.spring.malinda.thevideoapp.operations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

public class MediaStoreFacade {

    private Context context;

    public MediaStoreFacade(Context context)
    {

        this.context = context;
    }

    public Video getVideoById(long videoId) {
        // Build the Uri to the Video having given video Id.
        Uri videoUri =
                ContentUris.withAppendedId
                        (MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                videoId);

        // Cursor that is returned as a result of database query which
        // points to one row.
        try (Cursor cursor =
                     context.getContentResolver().query(videoUri,
                             null,
                             null,
                             null,
                             null)) {
            // Check if there is any row returned by the query.
            if (cursor.moveToFirst())
                // Get the Video metadata from Android Video Content
                // Provider
                return getVideo(cursor);
            else
                // Return null if there id no row returned by the
                // Query.
                return null;
        }
    }

    /**
     * Returns a collection of videos from content provider.
     */
    public List<VideoDecorator> getVideos(Uri videos)
    {
        List<VideoDecorator> all = new ArrayList<>();

        try (Cursor cursor =
                     context.getContentResolver().query(videos,
                             null,
                             null,
                             null,
                             null)) {
            // Check if there is any row returned by the query.
            if (cursor.moveToFirst())
                // Get the Video metadata from Android Video Content
                // Provider
            {
                while (!cursor.isAfterLast())
                {
                   String name = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_TITLE));
                   String rating = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING));
                   String videoId = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID));
                    String serverId = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_VIDEO_ID));

                    Video video = new Video(name, 0, "video/mp4");
                   VideoDecorator decorator = new VideoDecorator(video);
                    decorator.setServerId((long)Float.parseFloat(serverId));

                   video.setRating(Float.parseFloat(rating));
                   video.setVideoId(Long.parseLong(videoId));
                   all.add(decorator);
                   Log.i(Constants.TAG, "Reading data");
                   cursor.moveToNext();
                }

                return all;
            }
            else
                // Return null if there id no row returned by the
                // Query.
                return null;
        }
    }

    public boolean deleteVideoFromStore(Context context, long videoId)
    {
        Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId);
        int deleted = context.getContentResolver().delete(uri, null, null);

        Log.i(Constants.TAG, "Attempting to delete a stored video, returned value " + deleted);

        return deleted > 0;
    }

    private Video getVideo(Cursor cursor) throws IllegalArgumentException {
        // Get the Name of the Video, which is "videoName.mp4"
        String name =
                cursor.getString
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));

        // Get the Duration of the video.
        long duration =
                cursor.getLong
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

        // Get the MIME_TYPE of the video.
        String contentType =
                cursor.getString
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

        // Return the Instance of the Video having this fields.  You
        // may notice that we haven't set the Id, dataUrl of the
        // Video. It will be null before uploading and server will
        // generate this fields.
        return new Video(name,
                duration,
                contentType);
    }

    public String getVideoFilePath(long videoId){
        // Build the Uri to the Video having given video Id.
        Uri videoUri =
                ContentUris.withAppendedId
                        (MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                videoId);
        // Cursor that is returned as a result of database query which
        // points to one row.
        try (Cursor cursor =
                     context.getContentResolver().query(videoUri,
                             null,
                             null,
                             null,
                             null)) {
            // Check if there is any row returned
            // by the query.
            if (cursor.moveToFirst())
                // Get the file Path of Video having given
                // Id from Android Video Content Provider.
                return cursor.getString
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            else
                // Return null if there id no row
                // returned by the Query.
                return null;
        }
    }

    /**
     * Creates an implicit intent to play the video.
     */
    public Intent makePlayVideoIntent(Context context, Uri videoUri)
    {
        Log.i(Constants.TAG, "Making an intent to play video");

        Intent playVideo = new Intent(Intent.ACTION_VIEW);

        // TODO: We should be able to automatically determine the MIME type here.
        playVideo.setDataAndType(videoUri, "video/mp4");

        return playVideo;
    }
}
