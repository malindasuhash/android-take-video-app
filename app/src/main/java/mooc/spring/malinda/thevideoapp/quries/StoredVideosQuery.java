package mooc.spring.malinda.thevideoapp.quries;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

/**
 * Query to retrieve stored video information
 */
public class StoredVideosQuery {

    private Context context;

    public StoredVideosQuery(Context context)
    {
        this.context = context;
    }

    /**
     * @return a list of Videos stored in the data store.
     */
    public List<VideoEx> getAll(Uri videos) {

        List<VideoEx> all = new ArrayList<>(  );

        try (Cursor cursor = context.getContentResolver().query(videos,
                             null,
                             null,
                             null,
                             " created_date_time ")) {
            if (cursor.moveToFirst())
            {
                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_TITLE));
                    String desc = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_DESC));

                    VideoEx video = new VideoEx();
                    video.setTitle(title);
                    video.setDescription(desc);

                    all.add(video);
                    Log.i(Constants.TAG, "Reading data");
                    cursor.moveToNext();
                }

                return all;
            } else
                return new ArrayList<>();
        }
    }

}
