package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.CanUpdateInputs;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.commands.AddVideoToLocalStoreCommand;
import mooc.spring.malinda.thevideoapp.commands.RemoveVideoLocalCommand;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.operations.tasks.NewVideoDetailsTask;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.CreateFolderStep;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.Step;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.UploadFileStep;
import mooc.spring.malinda.thevideoapp.utils.DialogInfo;
import mooc.spring.malinda.thevideoapp.utils.L;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoNewOps implements OpsConfig, CanSetNewVideoDetails, DialogInfo {

    private WeakReference<Activity> mActivity;
    private final int MAX_TITLE_LEN = 25;
    private int currentTitleLen;
    private final int MAX_DESC_LEN = 40;
    private int currentDescLen;
    private MediaStoreVideo mStoredVideo;
    private Uri videoUri;
    private List<Step> videoUploadSteps = new ArrayList<>();

    public VideoNewOps()
    {
        videoUploadSteps.add(new CreateFolderStep());
        videoUploadSteps.add(new UploadFileStep());
    }

    @Override
    public void whenYesClicked() {
        removeVideoLocally();
        Toaster.Show(getActivity(), getActivity().getString(R.string.marked_removal));
        getActivity().finish();
    }

    @Override
    public void whenCancelClicked() {
    }

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
     * Adds the video information to the database.
     */
    public void addVideoToLocalStore()
    {
        VideoEx video = getVideo();

        AddVideoToLocalStoreCommand command = new AddVideoToLocalStoreCommand();
        command.addVideo(mActivity.get().getApplicationContext(), video);
    }

    /**
     * Stores the video locally and uploads to server.
     */
    public void storeAndUploadVideo()
    {
        addVideoToLocalStore();

        VideoEx video = getVideo();

        // Now upload
        try
        {
            for (Step step : videoUploadSteps)
            {
                step.execute(video);
            }
        }
        catch (Exception ex)
        {
            Toaster.Show(getActivity(), getActivity().getString(R.string.sorry_there_is_a_problem));
        }
    }

    private VideoEx getVideo()
    {
        String title = ((EditText)getActivity().findViewById(R.id.vid_t)).getText().toString();
        String desc = ((EditText)getActivity().findViewById(R.id.des_t)).getText().toString();

        if (currentTitleLen < 0) {
            title = title.substring(0, MAX_TITLE_LEN - 1); // trimed
        }

        if (currentDescLen < 0) {
            desc = desc.substring(0, MAX_DESC_LEN - 1); // trimmed
        }

        VideoEx video = VideoEx.map(title.length() == 0 ? mStoredVideo.getName() : title, desc, videoUri.toString(), mStoredVideo, UUID.randomUUID().toString(), false);

        return video;
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
