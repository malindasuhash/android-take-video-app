package mooc.spring.malinda.thevideoapp.retrofit;

import mooc.spring.malinda.thevideoapp.operations.Video;
import retrofit.http.Body;
import retrofit.http.POST;

public interface VideoSvc {

    /**
     * Adds the video meta data to the service.
     * @return the data path where the binary can be uploaded to.
     */
    @POST("/video")
    VideoMetaDto addVideo(@Body Video video);
}
