package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.retrofit.AppRoot;
import mooc.spring.malinda.thevideoapp.retrofit.FolderContentResponse;
import mooc.spring.malinda.thevideoapp.retrofit.FolderCreationRequest;
import mooc.spring.malinda.thevideoapp.retrofit.FolderCreationResponse;
import mooc.spring.malinda.thevideoapp.retrofit.VideoRemoteSvc;
import mooc.spring.malinda.thevideoapp.utils.Interceptor;
import mooc.spring.malinda.thevideoapp.utils.L;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by malindasuhash on 03/08/15.
 */
public class TestApiTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.onedrive.com/v1.0/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(Interceptor.Create())
                .build();

        VideoRemoteSvc service = restAdapter.create(VideoRemoteSvc.class);

        AppRoot root = service.getRoot();
        try
        {
            FolderCreationRequest request = new FolderCreationRequest();
            request.name = "malinda";
            request.folder = new Object();

            FolderCreationResponse response = service.createFolder(request);
            L.logI(response.id);
        }
        catch (RetrofitError error)
        {
            if (error.getResponse().getStatus() == 409) {
                L.logI("Name already exists!");
            }
        }




        FolderContentResponse response1 = service.getFolderContent();
        L.logI(String.valueOf(response1.value.length));

        L.logI(root.name);

        return null;
    }
}
