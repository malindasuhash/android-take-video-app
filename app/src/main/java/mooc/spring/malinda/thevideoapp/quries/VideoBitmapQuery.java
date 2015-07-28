package mooc.spring.malinda.thevideoapp.quries;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;

/**
 * Query to get the bitmap for the video.
 */
public class VideoBitmapQuery {

    private Context context;

    public VideoBitmapQuery(Context context)
    {

        this.context = context;
    }

    public Bitmap getBitmap(long videoId)
    {
        Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                videoId, MediaStore.Video.Thumbnails.MINI_KIND, null);

        return bitmap;
    }
}
