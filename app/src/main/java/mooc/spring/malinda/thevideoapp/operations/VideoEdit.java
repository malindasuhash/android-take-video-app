package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;

import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.framework.OpsConfig;

public class VideoEdit implements OpsConfig {

    private WeakReference<Activity> mActivity;

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {
        mActivity = new WeakReference<>(activity);

        if (firstTimeIn)
        {

        }
    }


}
