package mooc.spring.malinda.thevideoapp.operations;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

public class VideoStorageHandler {

    public void store(Context context, String title, String dataUrl, float rating, long duration, long videoId, String localVidId)
    {
        ContentValues values = new ContentValues();

        values.put(VideoDiaryContract.VideoEntry.COLUMN_TITLE, title);
        values.put(VideoDiaryContract.VideoEntry.COLUMN_CONTENT_TYPE, "video/mp4");
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DATA_URL, dataUrl);
        values.put(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING, Float.toString(rating));
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DURATION, Long.toString(duration));
        values.put(VideoDiaryContract.VideoEntry.COLUMN_VIDEO_ID, Long.toString(videoId));
        values.put(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID, localVidId);

        Log.i(Constants.TAG, "Local video id " + localVidId);

        Uri resolver = context.getContentResolver()
                .insert(VideoDiaryContract.VideoEntry.CONTENT_URI, values);

        Log.i(Constants.TAG, "Store result " + resolver);
    }
}
