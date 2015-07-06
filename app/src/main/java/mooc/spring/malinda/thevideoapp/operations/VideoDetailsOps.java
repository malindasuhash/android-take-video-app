package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;

public class VideoDetailsOps implements OpsConfig {

    private WeakReference<Activity> mActivity;
    private String mFilePath;
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
        this.mFilePath = mFacade.getVideoFilePath(videoId);

        File videoFile = new File(this.mFilePath);

        setVideoProperties(videoFile);
        setVideoThumbnail(videoId);
    }

    /**
     * Setting the video thumbnail.
     */
    public void setVideoThumbnail(final long videoId)
    {
        Log.i(Constants.TAG, "Setting the video thumbnail");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(getActivity().getContentResolver(),
                        videoId, MediaStore.Video.Thumbnails.MINI_KIND, null);
                ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * Sets properties of the video (such as location, size etc)
     */
    private void setVideoProperties(File file)
    {
        long size =  file.length() / Constants.MEGA_BYTE;

        Log.i(Constants.TAG, "Size is " + file.length());

        ((TextView) getActivity().findViewById(R.id.location)).setText(file.getPath());
        ((TextView)getActivity().findViewById(R.id.size)).setText(Long.toString(size) + " MB");
    }

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);

        if (firstTimeIn) {
            mFacade = new MediaStoreFacade(getActivity().getApplicationContext());
        }
    }
}
