package mooc.spring.malinda.thevideoapp.quries;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.utils.L;

/**
 * Container for queries to get data from Media Store.
 */
public class VideoMediaStoreQuery {

    private Context context;

    public VideoMediaStoreQuery(Context context)
    {

        this.context = context;
    }

    public MediaStoreVideo getVideoById(long id)
    {
        // Build the Uri to the Video having given video Id.
        Uri videoUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

        // Cursor that is returned as a result of database query which
        // points to one row.
        try (Cursor cursor = context.getContentResolver().query(videoUri,
                             null,
                             null,
                             null,
                             null)) {
            // Check if there is any row returned by the query.
            if (cursor.moveToFirst())
                // Get the Video metadata from Android Video Content
                // Provider
                return getVideo(cursor, videoUri);
            else
                // Return null if there id no row returned by the
                // Query.
                return null;
        }
    }

    private MediaStoreVideo getVideo(Cursor cursor, Uri videoUri)
    {
        // Get the Name of the Video, which is "videoName.mp4"
        String name =
                cursor.getString
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));

        // Get the MIME_TYPE of the video.
        String contentType =
                cursor.getString
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

        long dateTaken =
                cursor.getLong
                        (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));

        String location = cursor.getString
                (cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

        MediaStoreVideo video = new MediaStoreVideo();
        video.setName(name);
        video.setMimeType(contentType);
        video.setDateTaken(dateTaken);
        video.setLocation(location);

        // Following seems to return most accurate duration for the given video.
        MediaMetadataRetriever data = new MediaMetadataRetriever();
        data.setDataSource(context, videoUri);
        String videoDuration = data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        L.logI("Video duration " + videoDuration);

        video.setDuration(Long.parseLong(videoDuration));

        return video;

    }
}
