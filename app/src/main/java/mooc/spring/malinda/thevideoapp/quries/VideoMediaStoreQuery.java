package mooc.spring.malinda.thevideoapp.quries;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;

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
                return null;
                //return getVideo(cursor);
            else
                // Return null if there id no row returned by the
                // Query.
                return null;
        }
    }
}
