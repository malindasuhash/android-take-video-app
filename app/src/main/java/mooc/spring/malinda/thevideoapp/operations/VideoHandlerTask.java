package mooc.spring.malinda.thevideoapp.operations;

import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.retrofit.VideoMetaDto;
import mooc.spring.malinda.thevideoapp.retrofit.VideoSvc;
import mooc.spring.malinda.thevideoapp.services.TaskData;
import retrofit.RestAdapter;

public class VideoHandlerTask extends AsyncTask<TaskData, Void, VideoMetaDto> {

    private VideoSvc mService;

    public VideoHandlerTask()
    {
        mService = new RestAdapter.Builder()
                .setEndpoint(Constants.Server_Url)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(VideoSvc.class);
    }

    /**
     * Uploads the video to the server.
     */
    @Override
    protected VideoMetaDto doInBackground(TaskData... data) {
        Video v = data[0].getVideo();
        VideoMetaDto metaDto = mService.addVideo(v);
        return metaDto;
    }
}
