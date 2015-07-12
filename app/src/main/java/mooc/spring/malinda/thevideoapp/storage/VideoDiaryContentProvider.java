package mooc.spring.malinda.thevideoapp.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;

public class VideoDiaryContentProvider extends ContentProvider {

    private Context mContext;
    private SqlDbHelper mDbHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return VideoDiaryContract.VideoEntry.CONTENT_ITEM_TYPE;
    }

    /**
     * Adds a given item to the data source.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.i(Constants.TAG, "Attempting to add values");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long added = db.insert(VideoDiaryContract.VideoEntry.TABLE_NAME, null, values);

        Log.i(Constants.TAG, "Added status " + added);

        if (added > 0)
        {
            Uri current = VideoDiaryContract.VideoEntry.buildUri(added);
            Log.i(Constants.TAG, "Item added, Uri is " + current.toString());

            // Telling the world that there is an new item.
            mContext.getContentResolver().notifyChange(current, null);
            return current;
        }

        throw new android.database.SQLException("Failed to add record.");
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new SqlDbHelper(mContext);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        return db.query(VideoDiaryContract.VideoEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID, values.get(Constants.NewVideoId).toString());

        String oldVideoCheck = VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID + '=' + values.getAsString(Constants.OldVideoId);

        int affected = db.update(VideoDiaryContract.VideoEntry.TABLE_NAME, vals, oldVideoCheck, null);

        Log.i(Constants.TAG, "Affected rows by update " + affected);

        if (affected > 0)
        {
            // Telling the world that something has changed.
            mContext.getContentResolver().notifyChange(VideoDiaryContract.VideoEntry.CONTENT_URI, null);
        }

        return affected;
    }
}
