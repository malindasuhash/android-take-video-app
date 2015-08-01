package mooc.spring.malinda.thevideoapp.storage;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class VideoDiaryContract {

    public static final String CONTENT_AUTHORITY = "video.diary.simple";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CHARACTER = VideoEntry.TABLE_NAME;

    public static final class VideoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_CHARACTER).build();

        public static final String CONTENT_ITEMS_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY
                        + "/"
                        + PATH_CHARACTER;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/"
                        + CONTENT_AUTHORITY
                        + "/"
                        + PATH_CHARACTER;


        public final static String TABLE_NAME = "VideoDiary";
        public static String COLUMN_REFERENCE = "reference";
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_DURATION = "duration";
        public static String COLUMN_CONTENT_TYPE = "content_type";
        public static String COLUMN_DATA_URL = "data_url";
        public static String COLUMN_DESC = "description";
        public static String COLUMN_STAR_RATING = "star_rating";
        public static String COLUMN_VIDEO_ID = "video_id";
        public static String COLUMN_LOCAL_VIDEO_ID = "video_id_local";
        public static String COLUMN_CREATED_DATETIME = "created_date_time";

        /**
         * Provides the uri of the active/change record(s).
         */
        public static Uri buildUri(Long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
