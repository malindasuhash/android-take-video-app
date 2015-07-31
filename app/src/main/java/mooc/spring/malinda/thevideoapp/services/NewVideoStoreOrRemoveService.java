package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import mooc.spring.malinda.thevideoapp.framework.Constants;
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
}
