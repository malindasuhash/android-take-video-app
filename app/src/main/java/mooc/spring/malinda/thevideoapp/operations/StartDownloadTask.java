package mooc.spring.malinda.thevideoapp.operations;

import android.content.Intent;
import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.services.TaskData;
import mooc.spring.malinda.thevideoapp.services.VideoDownloadService;

public class StartDownloadTask extends AsyncTask<TaskData, Void, Void> {

    @Override
    protected Void doInBackground(TaskData... data) {

        Intent intent = new Intent(data[0].getContext(), VideoDownloadService.class);
        intent.putExtra(Constants.VideoId, data[0].getServerId());
        intent.putExtra(Constants.OldVideoId, data[0].getVideo().getVideoId());
        data[0].getContext().startService(intent);

        return null;
    }
}
