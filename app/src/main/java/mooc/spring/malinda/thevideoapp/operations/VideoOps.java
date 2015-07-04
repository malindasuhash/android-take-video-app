package mooc.spring.malinda.thevideoapp.operations;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.activities.MainActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;

public class VideoOps {

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private WeakReference<MainActivity> mActivity;

    private MainActivity getActivity()
    {
        return mActivity.get();
    }

    /**
     * Uses the build in video application to take the video.
     */
    public void takeVideo()
    {
        Log.i(Constants.TAG, "Making the intent to call build-in camera to take a video.");

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    public void whenTakingVideoCompletes(int requestCode, int resultCode, Intent data)
    {
        Log.i(Constants.TAG, "Video recording complete, callback received.");
    }

    public void onConfiguration(MainActivity activity,
                                boolean firstTimeIn) {
        // Create a WeakReference to the activity.

        mActivity = new WeakReference<>(activity);

        if (firstTimeIn) {
            // Initialize the Google account information.

        }
    }
}
