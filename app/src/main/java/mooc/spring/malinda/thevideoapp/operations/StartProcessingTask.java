package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.services.TaskData;
import mooc.spring.malinda.thevideoapp.services.VideoUploaderService;

public class StartProcessingTask extends AsyncTask<TaskData, TaskData, Void> {

    /**
     * Starts off the work in a background process to upload meta data and
     * update the database with data url.
     */
    @Override
    protected Void doInBackground(TaskData... data) {
        Video video = data[0].getVideo();
        Context context = data[0].getContext();

        Intent intent = VideoUploaderService.makeUploadVideo(context, video);
        context.startService(intent);
        return null;
    }

}
