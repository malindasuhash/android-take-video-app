package mooc.spring.malinda.thevideoapp.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Creates the database to store the video information.
 */
public class SqlDbHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 6;

    private static final String DATABASE_NAME = "video.diary";

    public SqlDbHelper(Context context) {
        super(context, context.getCacheDir()
                + File.separator + DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + VideoDiaryContract.VideoEntry.TABLE_NAME + " ("
                + VideoDiaryContract.VideoEntry._ID + " INTEGER PRIMARY KEY, "
                + VideoDiaryContract.VideoEntry.COLUMN_TITLE + " TEXT, "
                + VideoDiaryContract.VideoEntry.COLUMN_DURATION + " TEXT, "
                + VideoDiaryContract.VideoEntry.COLUMN_CONTENT_TYPE + " TEXT, "
                + VideoDiaryContract.VideoEntry.COLUMN_DATA_URL + " TEXT, "
                + VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING + " REAL, "
                + VideoDiaryContract.VideoEntry.COLUMN_VIDEO_ID + " INTEGER, "
                + VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID + " INTEGER "
                + " )";

            sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE " + VideoDiaryContract.VideoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
