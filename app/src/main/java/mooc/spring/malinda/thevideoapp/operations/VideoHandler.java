package mooc.spring.malinda.thevideoapp.operations;

import java.io.File;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.retrofit.VideoMetaDto;
import mooc.spring.malinda.thevideoapp.retrofit.VideoStatus;
import mooc.spring.malinda.thevideoapp.retrofit.VideoSvc;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

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
     * Uploads the video meta to the server.
     */
    public VideoMetaDto uploadMetaData(Video video) {
        VideoMetaDto metaDto = mService.addVideo(video);
        return metaDto;
    }

    /**
     * Uploads the video content to the server.
     */
    public VideoStatus uploadVideoContent(Video video)
    {
        File videoFile = new File(video.getPath());
        VideoStatus status = mService.storeVideo(video.getVideoId(), new TypedFile(video.getContentType(), videoFile));
        return status;
    }

    /**
     * Downloads the video from server.
     */
    public Response downloadVideo(long id)
    {
        Response response = mService.downloadVideoData(id);
        return response;
    }

    /**
     * Updates the new rating on the server.
     */
    public void updateNewRating(long id, float newRating)
    {
        mService.setNewRating(id, newRating);
    }
}
