package mooc.spring.malinda.thevideoapp.retrofit;


import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.utils.Interceptor;
import mooc.spring.malinda.thevideoapp.utils.L;
import retrofit.RestAdapter;

public class ServiceEndPointAdapter {

    /**
     * Creates a proxy to upload data to remote server.
     */
    public static VideoRemoteSvc create()
    {
        L.logI("Creating an adapter to call remote endpoint.");
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.Server_Url)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(Interceptor.Create())
                .build();

        return restAdapter.create(VideoRemoteSvc.class);
    }
}
