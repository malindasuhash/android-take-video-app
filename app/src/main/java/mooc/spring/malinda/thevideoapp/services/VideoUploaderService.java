package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.Video;
import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.CreateFolderStep;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.Step;
import mooc.spring.malinda.thevideoapp.services.uploadSteps.UploadFileStep;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoUploaderService extends IntentService {

    private List<Step> videoUploadSteps = new ArrayList<>();

    public VideoUploaderService() {
        super("VideoUploaderService");
        videoUploadSteps.add(new CreateFolderStep());
        videoUploadSteps.add(new UploadFileStep());
    }

    /**
     * Intent to store and upload the video.
     */
    public static Intent makeUploadVideo(Context context, Video video)
    {
        Log.i(Constants.TAG, "Creating the store and upload intent.");

        Intent storeAndUpload = new Intent(context, VideoUploaderService.class);
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_TITLE, video.getName());
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_CONTENT_TYPE, video.getContentType());
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_DATA_URL, "from server");
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_DURATION, video.getDuration());
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING, video.getRating());
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID, video.getVideoId());
        storeAndUpload.putExtra(Constants.VideoPath, video.getPath());

        return storeAndUpload;
    }

    /**
     * Makes an intent to upload the video.
     */
    public static Intent makeIntent(Context context, VideoEx video)
    {
        Intent intent = new Intent(context, VideoUploaderService.class);
        intent.putExtra(Constants.Video, video);
        return intent;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            try
            {
                for (Step step : videoUploadSteps)
                {
                    step.execute((VideoEx)intent.getSerializableExtra(Constants.Video));
                }
            }
            catch (Exception ex)
            {
                Toaster.Show(this, this.getString(R.string.sorry_there_is_a_problem));
            }

            /*VideoHandler mVideoHandler = new VideoHandler();
            VideoStorageHandler mStorage = new VideoStorageHandler();

            Log.i(Constants.TAG, "Received call in the service.");
            String title = intent.getStringExtra(VideoDiaryContract.VideoEntry.COLUMN_TITLE);
            long duration = intent.getLongExtra(VideoDiaryContract.VideoEntry.COLUMN_DURATION, 0);
            float ratings = intent.getFloatExtra(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING, 0);
            long localVideoId = intent.getLongExtra(VideoDiaryContract.VideoEntry.COLUMN_LOCAL_VIDEO_ID, 0);
            String videoPath = intent.getStringExtra(Constants.VideoPath);

            Log.i(Constants.TAG, "Video id in callback " + localVideoId);

            Video video = new Video(title, duration, "video/mp4");
            video.setRating(ratings);
            video.setPath(videoPath);

            NotificationHandler.startNotification(this, true);

            // Uploads meta data to the server and obtains the data uri.
            VideoMetaDto metaDto = mVideoHandler.uploadMetaData(video);
            Log.i(Constants.TAG, "Meta data uploaded, saving the state now");

            // Setting the video id so that it match the server.
            video.setVideoId(metaDto.getVideoId());

            // Upload video.
            VideoStatus status = mVideoHandler.uploadVideoContent(video);

            if (status.getState() != VideoStatus.VideoState.READY)
            {
                Toaster.Show(this.getApplicationContext(), "Sorry, there was a problem uploading the video to server.");
                return;
            }

            Log.i(Constants.TAG, "Now update the UI");

            mStorage.store(this.getApplicationContext(), video.getName(),
                    metaDto.getDataUrl(), video.getRating(), video.getDuration(),
                    video.getVideoId(), Long.toString(localVideoId));

            NotificationHandler.finishNotification(this.getApplicationContext(), true);

            BroadcastRefresh.broadcastRefresh(this.getApplicationContext());*/
        }
    }
}
