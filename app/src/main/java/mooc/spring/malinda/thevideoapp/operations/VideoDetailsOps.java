package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;

public class VideoDetailsOps implements OpsConfig {

    private WeakReference<Activity> mActivity;
    private MediaStoreFacade mFacade;

    private VideoDetailsActivity getActivity()
    {
        return (VideoDetailsActivity)mActivity.get();
    }

    /**
     * Loads the video data to view.
     */
    public void loadVideoData(Intent data)
    {
        long videoId = data.getLongExtra("videoId", 0);

        Log.i(Constants.TAG, "Video id from intent " + videoId);

        Video video = mFacade.getVideoById(videoId);
        String filePath = mFacade.getVideoFilePath(videoId);

        setVideoProperties(video, filePath);
    }

    /**
     * Sets properties of the video (such as location, size etc)
     */
    private void setVideoProperties(Video video, String filePath)
    {
        File videoFile = new File(filePath);

        long size =
                videoFile.length() / Constants.MEGA_BYTE;

        ((TextView) getActivity().findViewById(R.id.location)).setText(filePath);
        ((TextView)getActivity().findViewById(R.id.size)).setText(Long.toString(size));
    }

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);

        if (firstTimeIn) {
            mFacade = new MediaStoreFacade(getActivity().getApplicationContext());
        }
    }
}
