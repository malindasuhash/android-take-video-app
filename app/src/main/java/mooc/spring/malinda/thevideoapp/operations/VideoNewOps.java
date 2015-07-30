package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.CanUpdateInputs;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.operations.tasks.NewVideoDetailsTask;
import mooc.spring.malinda.thevideoapp.services.TaskData;
import mooc.spring.malinda.thevideoapp.utils.L;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoNewOps implements OpsConfig, CanSetNewVideoDetails {

    private WeakReference<Activity> mActivity;
    private WeakReference<RatingBar> mRatings;

    private String mFilePath;
    private long mVideoId;
    private MediaStoreFacade mFacade;
    private float mCurrentRating;
    private boolean hasSizeExceeded;
    private final int MAX_TITLE_LEN = 15;
    private int currentTitleLen;
    private final int MAX_DESC_LEN = 30;
    private int currentDescLen;

    public enum Input
    {
        Title,
        Description
    }

    private VideoDetailsActivity getActivity()
    {
        return (VideoDetailsActivity)mActivity.get();
    }

    /**
     * Loads the video data to view.
     */
    public void loadVideoData(Intent data)
    {
        long videoId = data.getLongExtra(Constants.VideoId, 0);

        NewVideoDetailsTask task = new NewVideoDetailsTask();
        LoadDataDto dataDto = new LoadDataDto();
        dataDto.setContext(getActivity().getApplicationContext());
        dataDto.setCanSetNewVideoDetails(this);
        dataDto.setVideoId(videoId);
        task.execute(dataDto);

        L.logI("Video id from intent " + videoId);
        mVideoId = videoId;

        this.mFilePath = mFacade.getVideoFilePath(videoId);

        File videoFile = new File(this.mFilePath);

        setVideoProperties(videoFile);
        setVideoThumbnail(videoId);
    }

    public void updateInputLengths(Input input, String current, CanUpdateInputs canUpdateInputs)
    {
        if (input == Input.Title)
        {
            currentTitleLen = MAX_TITLE_LEN - current.length();
            canUpdateInputs.updateTitleLengh(currentTitleLen);

        } else
        {
            currentDescLen = MAX_DESC_LEN - current.length();
            canUpdateInputs.updateDescLength(currentDescLen);
        }

        L.logI("Title " + currentTitleLen + " Desc " + currentDescLen);
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
    public void storeDetailsAnduploadVideo()
    {
        if (hasSizeExceeded) {
            Toaster.Show(mActivity.get(), "Sorry the video should be less than 50MB");
            return;
        }

        Log.i(Constants.TAG, "Creating an intent to upload and store the video.");

        Video video = mFacade.getVideoById(mVideoId);
        video.setRating(mRatings.get().getRating());
        video.setPath(mFilePath);
        video.setVideoId(mVideoId);

        TaskData data = new TaskData();
        data.setContext(getActivity().getApplicationContext());
        data.setVideo(video);

        StartProcessingTask task = new StartProcessingTask();
        task.execute(data);

        Toaster.Show(getActivity().getApplicationContext(), "Uploading..");
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

        String sizeToShow;

        if (size < 1)
        {
            sizeToShow = file.length() + " Bytes";
        } else
        {
            sizeToShow = Long.toString(size) + " MB";
        }

        if (size >= 50)
        {
            hasSizeExceeded = true;
        }
    }

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);
        mRatings = new WeakReference<>((RatingBar)activity.findViewById(R.id.ratings));

        if (firstTimeIn) {
            mFacade = new MediaStoreFacade(getActivity().getApplicationContext());
            ((VideoDetailsActivity)mActivity.get()).updateTitleLengh(MAX_TITLE_LEN);
            ((VideoDetailsActivity)mActivity.get()).updateDescLength(MAX_DESC_LEN);
            currentTitleLen = MAX_TITLE_LEN;
            currentDescLen = MAX_DESC_LEN;
        } else
        {
            ((VideoDetailsActivity)mActivity.get()).updateTitleLengh(MAX_TITLE_LEN);
            ((VideoDetailsActivity)mActivity.get()).updateDescLength(MAX_DESC_LEN);
        }
    }

    @Override
    public void setNewVideoDetails(MediaStoreVideo mediaStoreVideo) {
        L.logI("Setting new video details callback.");
    }
}
