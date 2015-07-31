package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.lang.ref.WeakReference;

import mooc.spring.malinda.thevideoapp.activities.CanUpdateInputs;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.commands.RemoveVideoLocalCommand;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.operations.tasks.NewVideoDetailsTask;
import mooc.spring.malinda.thevideoapp.utils.L;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoNewOps implements OpsConfig, CanSetNewVideoDetails {

    private WeakReference<Activity> mActivity;
    private final int MAX_TITLE_LEN = 15;
    private int currentTitleLen;
    private final int MAX_DESC_LEN = 30;
    private int currentDescLen;
    private MediaStoreVideo mStoredVideo;
    private Uri videoUri;

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
     * Updates the number of characters in the title and description.
     */
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
        Intent intent = new Intent();
        intent.setDataAndType(videoUri, mStoredVideo.getMimeType());

        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            Log.i(Constants.TAG, "Invoking the intent to play video");
            getActivity().startActivity(intent);
        } else {
            Toaster.Show(getActivity(), "Sorry, there isn't any app to play back video.");
        }
    }

    /**
     * Removes the video from local store.
     */
    public void removeVideoLocally()
    {
        RemoveVideoLocalCommand command = new RemoveVideoLocalCommand();
        command.RemoveVideo(videoUri, getActivity().getApplicationContext());
    }

    /**
     * Stores and upload the video.

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

    */
    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);

        if (firstTimeIn) {
            ((VideoDetailsActivity)mActivity.get()).updateTitleLengh(MAX_TITLE_LEN);
            ((VideoDetailsActivity)mActivity.get()).updateDescLength(MAX_DESC_LEN);
            currentTitleLen = MAX_TITLE_LEN;
            currentDescLen = MAX_DESC_LEN;
            loadVideoInformation(activity);

        } else
        {
            ((VideoDetailsActivity)mActivity.get()).updateTitleLengh(MAX_TITLE_LEN);
            ((VideoDetailsActivity)mActivity.get()).updateDescLength(MAX_DESC_LEN);
            getActivity().setImageInfo(mStoredVideo);
        }
    }

    @Override
    public void setNewVideoDetails(MediaStoreVideo mediaStoreVideo) {
        L.logI("Setting new video details callback.");

        mStoredVideo = mediaStoreVideo;
        L.logI("Setting image info in the UI.");
        getActivity().setImageInfo(mStoredVideo);
    }

    /**
     * Loads details about the video in a non-UI thread and calls back
     * to setNewVideoDetails.
     */
    private void loadVideoInformation(Activity activity)
    {
        long videoId = activity.getIntent().getLongExtra(Constants.VideoId, 0);
        videoUri = Uri.parse(activity.getIntent().getStringExtra(Constants.VideoUri));

        NewVideoDetailsTask task = new NewVideoDetailsTask();
        LoadDataDto dataDto = new LoadDataDto();
        dataDto.setContext(getActivity().getApplicationContext());
        dataDto.setCanSetNewVideoDetails(this);
        dataDto.setVideoId(videoId);
        task.execute(dataDto);

        L.logI("Task to load image data started.");
    }
}
