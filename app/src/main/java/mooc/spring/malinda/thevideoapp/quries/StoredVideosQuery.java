package mooc.spring.malinda.thevideoapp.quries;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoInfo;
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
    public List<VideoInfo> getAll(Uri videos) {

        List<VideoInfo> all = new ArrayList<>(  );

        try (Cursor cursor = context.getContentResolver().query(videos,
                             null,
                             null,
                             null,
                             " created_date_time ")) {
            if (cursor.moveToFirst())
            {
                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_TITLE));
                    String rating = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING));
                    String videoId = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID));
                    String serverId = cursor.getString(cursor.getColumnIndex(VideoDiaryContract.VideoEntry.COLUMN_VIDEO_ID));

                    VideoInfo video = new VideoInfo();
                    video.setTitle(title);

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
