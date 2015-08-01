package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.UUID;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.L;


public class NewVideoStoreOrRemoveService extends IntentService {

    public NewVideoStoreOrRemoveService() {
        super("RemoveVideoFromLocalStoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (Constants.Remove.equals(action))
            {
                L.logI("Call for removing a video is received.");
                removeVideo(Uri.parse(intent.getStringExtra(Constants.VideoUri)));
            }
            if (Constants.Add.equals(action))
            {
                L.logI("Adding a new video to local store.");
                addVideo(intent);
            }
        }
    }

    private void addVideo(Intent data)
    {
        L.logI("Received the call to add the video.");
        VideoEx video = (VideoEx)data.getSerializableExtra(Constants.Video);
        L.logI("Video is " + video);

        Uri diaryResolver = VideoDiaryContract.VideoEntry.CONTENT_URI;
        L.logI("Resolver is " + diaryResolver);

        ContentValues values = new ContentValues();
        values.put(VideoDiaryContract.VideoEntry.COLUMN_TITLE, video.getTitle());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_CREATED_DATETIME, video.getDateTaken());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DURATION, video.getDuration());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_CONTENT_TYPE, video.getContentType());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DESC, video.getDescription());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_DATA_URL, video.getDataUrl());
        values.put(VideoDiaryContract.VideoEntry.COLUMN_REFERENCE, UUID.randomUUID().toString());

        Uri result = getContentResolver().insert(diaryResolver, values);
        L.logI("Add video result " + result);

        if (result != null)
        {
            getContentResolver().notifyChange(result, null);
        }
    }

    private void removeVideo(Uri videoUri)
    {
        L.logI("Removing video with Uri " + videoUri);
        int result = getContentResolver().delete(videoUri, null, null);

        if (result == 1) {
            L.logI("Removed successfully");
        } else
        {
            L.logI("Looks like there was a problem removing the given file. result =" + result);
        }
    }

    /**
     * Creates an intent to remove a video.
     */
    public static Intent makeRemoveVideoIntent(Context context, Uri videoUri)
    {
        L.logI("Attempt to remove the video");
        Intent intent = new Intent(context, NewVideoStoreOrRemoveService.class);
        intent.setAction(Constants.Remove);
        intent.putExtra(Constants.VideoUri, videoUri.toString());
        return intent;
    }

    /**
     * Creates an intent to add a video to local store.
     */
    public static Intent makeAddVideoIntent(Context context, VideoEx video)
    {
        L.logI("Creating an intent to add a video");
        Intent intent = new Intent(context, NewVideoStoreOrRemoveService.class);
        intent.setAction(Constants.Add);
        intent.putExtra(Constants.Video, video);
        return intent;
    }
}
