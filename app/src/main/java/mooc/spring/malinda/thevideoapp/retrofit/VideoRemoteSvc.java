package mooc.spring.malinda.thevideoapp.retrofit;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

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
}
