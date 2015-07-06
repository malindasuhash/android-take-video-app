package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoStorageHelper;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

public class VideoUploader extends IntentService {

    public VideoUploader() {
        super("VideoUploader");
    }

    /**
     * Intent to store and upload the video.
     */
    public static Intent makeUploadVideo(Context context, String title)
    {
        Log.i(Constants.TAG, "Creating the store and upload intent.");

        Intent storeAndUpload = new Intent(context, VideoUploader.class);
        storeAndUpload.putExtra(VideoDiaryContract.VideoEntry.COLUMN_TITLE, title);

        return storeAndUpload;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i(Constants.TAG, "Received call in the service.");
            VideoStorageHelper.store(getApplicationContext(), "title", "data", 2.3f, 101);
        }
    }
}
