package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoEdit implements OpsConfig {

    private MediaStoreFacade mMediaStoreFacade;
    private WeakReference<Activity> mActivity;
    private long mVideoId;
    private boolean mVideoExists;
    private String mFilePath;

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {
        mActivity = new WeakReference<>(activity);

        if (firstTimeIn)
        {
            mMediaStoreFacade = new MediaStoreFacade(mActivity.get().getApplicationContext());
            mVideoId = (long)mActivity.get().getIntent().getFloatExtra(Constants.VideoId, 0);
            mVideoExists = doesVideoExists();
        }

        if (mVideoExists)
        {
            // No need to show this as video exists.
            mActivity.get().findViewById(R.id.uploadVideo).setVisibility(View.INVISIBLE);
        }
    }

    public void playVideo()
    {
        Intent intent = mMediaStoreFacade.makePlayVideoIntent(mActivity.get(), Uri.parse(mFilePath));

        if (intent.resolveActivity(mActivity.get().getPackageManager()) != null)
        {
            Log.i(Constants.TAG, "Invoking the intent to play video");
            mActivity.get().startActivity(intent);
        } else {
            Toaster.Show(mActivity.get(), "Sorry, there isn't any app to play back video.");
        }
    }

    /**
     * Check whether the video exists in media store.
     */
    private boolean doesVideoExists()
    {
        Video video = mMediaStoreFacade.getVideoById(mVideoId);

        if (video != null) {
            Toaster.Show(mActivity.get(), "VideoExists!");

            // Get stored information.
            mFilePath = mMediaStoreFacade.getVideoFilePath(mVideoId);

            return true;
        }

        return false;
    }

}
