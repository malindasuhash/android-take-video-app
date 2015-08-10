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
                request.addHeader("Authorization", "5tF2IaRrh+O/tPa6CyIVCAL+FTyUikZBTN38imROhJ64EBrbDcgqPs6bsbTPMOY7FKBPXAy2Qre0BLhrxNhPhJ+IOhih8uk+FnHXuB8wAnnijHdEoC81WET3goCuqItVZfAvE9rBZfJdddJB+ndzadehpEZ3BIKAUgXeTB3k3m7M0qRMUPz/m/tpwzi/uvyNhK3NzoDAF8xeJns63F7ZqoKt2tABN7Zq4NRawgxBSK4+2hfmr+w47JOsgM1xlAImrNbYhLQH1kc3twC2QoPGjenGWxC41ZY0t8DOQi7Wpsjte6MuIbodR8DZgAACJ093fct5Xm+SAFCJrE+HxjRE+kBBbE6H0BW0YwsbF0a9w6DS2ZhTSb5GltD/hBSG6TgA8JOu41pd4CzsMLWkGYT2/XCfF59lSls4fsjuswxfyMSXdEPmWFptiRKYcJJVAAj+dbKPZHRhDqvB4c4O4OqO/HiVQV0lOpReYtGYn4/Ddw4S1je67uC4cuAoO2JiYAbxpn59UpzYrC4J0zGbsGc6Ar2CzOqL7KgY6m67k4QAsbqVTQsSG320RIgxyNE0px+7FmrnlYTP/M5O5j/B9x6fllH9t9iUOQSzMrySUHI3mjrBuwtJ6HS4cCqNHWcfHSvOos77j6BeuX6+SiL8gOByVn+x7NbUBkABdewAZA+8YMiDCdjI6LwoY8X2Sb0gKSXmBOt7tOnEAVxYVE4y+qUd/PfvsmhzFYDpVpO/Qo3VVbtURvqgXSoG7IY7gkg8s8eaQE=");
            }
        };

        return requestInterceptor;
    }
}
