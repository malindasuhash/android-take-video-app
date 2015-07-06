package mooc.spring.malinda.thevideoapp.operations;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

public class VideoStorageHelper {

    public static void store(Context context, String title, String dataUrl, float rating, long duration)
    {
        ContentValues values = new ContentValues();

        values.put(VideoDiaryContract.VideoEntry.COLUMN_TITLE, title);
        values.put(VideoDiaryContract.VideoEntry.COLUMN_CONTENT_TYPE, "video/mp4");
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DATA_URL, dataUrl);
        values.put(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING, Float.toString(rating));
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DURATION, Long.toString(duration));

        Uri resolver = context.getContentResolver()
                .insert(VideoDiaryContract.VideoEntry.CONTENT_URI, values);

        Log.i(Constants.TAG, "Store result " + resolver);
    }
}
