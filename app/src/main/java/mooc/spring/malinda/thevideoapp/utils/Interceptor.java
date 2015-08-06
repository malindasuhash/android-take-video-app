package mooc.spring.malinda.thevideoapp.utils;

import retrofit.RequestInterceptor;

public class Interceptor {

    /**
     * Creates an interceptor for retrofit to add the authorisation header.
     */
    public static RequestInterceptor Create()
    {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {

            // TODO: Store this auth key securely during oauth negotiation.
            @Override
            public void intercept(RequestFacade request) {
            }
        };

        return requestInterceptor;
    }
}
