package mooc.spring.malinda.thevideoapp.operations;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.retrofit.VideoMetaDto;
import mooc.spring.malinda.thevideoapp.retrofit.VideoSvc;
import retrofit.RestAdapter;

public class VideoHandler {

    private VideoSvc mService;

    public VideoHandler()
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
    public VideoMetaDto uploadMetaData(Video video) {
        VideoMetaDto metaDto = mService.addVideo(video);
        return metaDto;
    }
}
