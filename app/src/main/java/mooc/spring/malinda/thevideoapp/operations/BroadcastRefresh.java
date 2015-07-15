package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import mooc.spring.malinda.thevideoapp.framework.Constants;

public class BroadcastRefresh {

    /**
     * Broadcasts an intent to refresh the video list UI.
     */
    public static void broadcastRefresh(Context context)
    {
        Intent intent = new Intent(Constants.RefreshBoardcast);
        LocalBroadcastManager
                .getInstance(context)
                .sendBroadcast(intent);
    }
}
