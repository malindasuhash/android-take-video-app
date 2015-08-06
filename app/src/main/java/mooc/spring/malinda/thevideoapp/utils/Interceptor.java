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
                request.addHeader("Authorization", "bearer A2PoADnaslgwnhSAxBDQnM+DgDimr1ijwcThQWSayg0OcePMgWmuP/q3mtOHRmr+ysd5wiQ4zkQ/eU95HlwtLIURWlmBeo0Oy58FR82RQ4igvVrlADGGgspDRWSWFsY04ZQu9ECNntK2yNB8Kx0goW3iB/eisNZuIY+O4M2mJkuykQLkDMhM68JOuJeE5rs1V+aKkzFxIaSKNWoSLV9UKwQj+NrRym2M2i2rg/LjpB3CvmmwQyP79ZZFpFY1i+zTrvUUMLT5Wg0LLKTwwDZgAACGjia8rSUdJ9SAGeuHYCxbARh9/qWd2vyqpYoAXb8CKdKp8zVZ8uKmXzm7rXcsuNtCvEqESs/ShnoKzWsRWV6WE0wa37f2/u36j4mCCBJR/JUv3asPNGSkiFbdJSFNon13Y7+N+/Q61bcdQ8TF76TF7nUKFfD4gGRj82jE4aOX8t/le3zqNIfSxVSmmEWA/YdJjDv7Zwg1AjwP8qwV3pzmky60Z+ip144O3z/ZMJurZDAs9zQGSpQh0+P06zlUdcozC0fo3V2K2XZJzoyQPFizpM0bLsOehhMTj5vM0yCoB4CmQUyZfUSibrt9XIlMwlNy5WUDRnRQHWDC9yMKBb0SSYRLfeY0Yfd2xjap5et8JNtljqcIHMe15dJ1Is7kwEN+93ICA+SenQIwG6PlALtG9jQzIBo6iGXwF7qFGdd+FurNyfbVMtSTwK3UEPkiM+njsLaQE=");
            }
        };

        return requestInterceptor;
    }
}
