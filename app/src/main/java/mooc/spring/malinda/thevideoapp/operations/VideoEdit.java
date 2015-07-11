package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;

import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoEdit implements OpsConfig {

    private WeakReference<Activity> mActivity;
    private long mVideoId;
    private boolean mVideoExists;

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {
        mActivity = new WeakReference<>(activity);

        if (firstTimeIn)
        {
            mVideoId = (long)mActivity.get().getIntent().getFloatExtra(Constants.VideoId, 0);
            mVideoExists = doesVideoExists();
        }
    }

    private boolean doesVideoExists()
    {
        MediaStoreFacade mediaStoreFacade = new MediaStoreFacade(mActivity.get().getApplicationContext());
        Video video = mediaStoreFacade.getVideoById(mVideoId);

        if (video != null) {
            Toaster.Show(mActivity.get(), "VideoExists!");
            return true;
        }

        return false;
    }

}
