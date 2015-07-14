package mooc.spring.malinda.thevideoapp.retrofit;

import mooc.spring.malinda.thevideoapp.operations.Video;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Streaming;
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

    /**
     * Downloads the video from server.
     */
    @Streaming
    @GET("/video/{id}/data")
    Response downloadVideoData(@Path("id") long id);

    /**
     * Updates the ratings in the server.
     */
    @PUT("/video/{id}/{rating}")
    boolean setNewRating(@Path("id") long id, @Path("rating") float newRating);

    /**
     * Gets the calculated rating from server.
     */
    @GET("/video/{id}/rating")
    float getRating(@Path("id") long id);
}
