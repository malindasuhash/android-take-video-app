package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import mooc.spring.malinda.thevideoapp.services.VideoUploader;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoDetailsOps implements OpsConfig {

    private WeakReference<Activity> mActivity;
    private String mFilePath;
    private long mVideoId;
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
        mVideoId = videoId;


        this.mFilePath = mFacade.getVideoFilePath(videoId);

        File videoFile = new File(this.mFilePath);

        setVideoProperties(videoFile);
        setVideoThumbnail(videoId);
    }

    /**
     * Plays the video using the implicit intent.
     */
    public void playVideo()
    {
        Intent intent = mFacade.makePlayVideoIntent(getActivity(), Uri.parse(mFilePath));

        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            Log.i(Constants.TAG, "Invoking the intent to play video");
            getActivity().startActivity(intent);
        } else {
            Toaster.Show(getActivity(), "Sorry, there isn't any app to play back video.");
        }
    }

    /**
     * Stores and upload the video.
     */
    public void uploadVideo()
    {
       Intent intent = VideoUploader.makeUploadVideo(getActivity(), "title");
       getActivity().startService(intent);
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

        String sizeToShow;

        if (size < 1)
        {
            sizeToShow = file.length() + " Bytes";
        } else
        {
            sizeToShow = Long.toString(size) + " MB";
        }

        ((TextView)getActivity().findViewById(R.id.size)).setText(sizeToShow);
    }

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);

        if (firstTimeIn) {
            mFacade = new MediaStoreFacade(getActivity().getApplicationContext());
        }
    }
}
