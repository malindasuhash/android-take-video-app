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
                request.addHeader("Authorization", "bearer EwB4Aq1DBAAUGCCXc8wU/zFu9QnLdZXy+YnElFkAAU94y3x6seyyrjtXairadiRmR4+50c7iqlcqthI8F08rlmeeiuVUznU5USm4rk4oLGEFTm1fncgKIFOaueoIyXYT3OZTq9wOsCzMb+GsN1OEM4qT260Ja03ylvpznCEbIi9fnFu9XzyFKHuLtEvzsKyBRdF/Sc6NmrW4nyAdbo68q28muGLv3qya2Jl8dmiHB7UhadzITJZ9h8c4AhDo/2rYziUQ+5WhwJbEN5VplGiEx/aEUWvntLs6YxUhpu198H4KsYZ52UE7arPf10NS2BVPxFu7ftp/CxDl2o2V9H5O4p5gcLiEBBAm/A5lVYvlua++Ooi0uAFI6Vxy39JF9A0DZgAACFF53Wp637mlSAGyWnW3WcpWruPCi59JZtynS9gZ8JKgLWcBZAWtFAIl6M+vk5hPlU42zoCgOqro7sdg7vEcf8HAyAgBhaMxUz3Kl+fhz5X5voZU8ZTLHDsleHoq113oaCQ/Eb71Z5nDGDeIcQNf/tyzF0/NyjT+NVIlvkrjRl+pbUo+e/MuGztAXr2E92tx+1zalQIHbU1ouJhzBpniyTUeLsRSVNU0L+pss8uDZW5W0N2T5aCAvBu+28DIRk4Vq+hngND2oQ4o9b8p4S6SYbBeD3p1jxBceC5tyjp/tj6QG9UL2SkQ4+rZvjogYro4Ijtj+jNY0NB0C9outaqYLodCh6UExW2uxt/0+wPNLmHFtckj34V+spPFPpzy5Prc//ac43BCMjdaee3lZPpIeXKxcXu9qaek2UaoRg/s9tefqZ/GdPt9u1pBejvXGwL7nxhTaQE=");
            }
        };

        return requestInterceptor;
    }
}
