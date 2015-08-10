package mooc.spring.malinda.thevideoapp.retrofit;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * The abstraction that wraps retrofit to call a storage provider.
 */
public interface VideoRemoteSvc {

    @GET("/drive/special/approot")
    AppRoot getRoot();

    @POST("/drive/special/approot/children?nameConflict=fail")
    FolderCreationResponse createFolder(@Body FolderCreationRequest request);

    @GET("/drive/special/approot/children")
    FolderContentResponse getFolderContent();

    @Multipart
    @PUT("/drive/special/approot:/{folderName}/{fullFileName}:/content")
    AppRoot uploadVideo(@Path("folderName") String folderName, @Path("fullFileName") String fileName, @Part("text") TypedFile text);
}
