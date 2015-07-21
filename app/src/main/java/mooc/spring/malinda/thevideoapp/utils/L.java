package mooc.spring.malinda.thevideoapp.utils;

import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;

/**
 * Simple wrapper around the logging framework.
 */
public class L {
    public static void logI(String message)
    {
        Log.i(Constants.TAG, message);
    }
}
