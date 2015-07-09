package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.Video;
import mooc.spring.malinda.thevideoapp.operations.VideoHandlerTask;
import mooc.spring.malinda.thevideoapp.operations.VideoStorageHandler;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoUploaderService extends IntentService {

    private VideoStorageHandler mStorage = new VideoStorageHandler();

    public VideoUploaderService(String name) {
        super(name);
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

        return storeAndUpload;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i(Constants.TAG, "Received call in the service.");
            String title = intent.getStringExtra(VideoDiaryContract.VideoEntry.COLUMN_TITLE);
            long duration = intent.getLongExtra(VideoDiaryContract.VideoEntry.COLUMN_DURATION, 0);
            float ratings = intent.getFloatExtra(VideoDiaryContract.VideoEntry.COLUMN_STAR_RATING, 0);

            Video video = new Video(title, duration, "video/mp4");
            video.setRating(ratings);

            // Uploads meta data to the server and obtains the data uri.
            TaskData data = new TaskData();
            data.setVideo(video);
            data.setContext(getApplicationContext());

            VideoHandlerTask task = new VideoHandlerTask();
            task.execute(data);

            Toaster.Show(this, "Saved locally and with meta data from server.");
        }
    }
}
