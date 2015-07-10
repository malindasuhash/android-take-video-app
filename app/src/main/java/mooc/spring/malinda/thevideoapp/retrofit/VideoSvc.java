package mooc.spring.malinda.thevideoapp.retrofit;

import mooc.spring.malinda.thevideoapp.operations.Video;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

public interface VideoSvc {

    /**
     * Adds the video meta data to the service.
     * @return the data path where the binary can be uploaded to.
     */
    @POST("/video")
    VideoMetaDto addVideo(@Body Video video);

    /**
     * Uploads the video content to the server.
     */
    @Multipart
    @POST("/video/{id}/data")
    VideoStatus storeVideo(@Path("id") long id, @Part("data")TypedFile videoData);
}
