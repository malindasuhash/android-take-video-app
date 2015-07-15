package mooc.spring.malinda.thevideoapp.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import mooc.spring.malinda.thevideoapp.operations.BroadcastRefresh;

public class NotificationHandler {

    private static int NOTIFICATION_ID = 1;

    /**
     * Starts the Notification to show the progress of video upload.
     */
    public static void startNotification(Context context, boolean isUpload) {
        // Gets access to the Android Notification Service.
        NotificationManager mNotifyManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(isUpload ? "Video Upload" : "Video Download")
                        .setContentText(isUpload ? "Upload in progress" : "Download in progress")
                        .setSmallIcon(isUpload ? android.R.drawable.stat_sys_upload : android.R.drawable.stat_sys_download)
                        .setTicker(isUpload ? "Uploading video" : "Downloading video")
                        .setProgress(0,
                                0,
                                true);

        // Build and issue the notification.
        mNotifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());
    }

    public static void finishNotification(Context context, boolean isUpload) {
        // When the loop is finished, updates the notification.
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
                .setContentTitle(isUpload ? "Upload complete" : "Download complete")
                        // Removes the progress bar.
                .setProgress(0,
                        0,
                        false)
                .setSmallIcon(isUpload ? android.R.drawable.stat_sys_upload_done : android.R.drawable.stat_sys_download_done)
                .setContentText("")
                .setTicker(isUpload ? "Upload complete!!" : "Download complete!!");

        // Build the Notification with the given
        // Notification Id.
        NotificationManager mNotifyManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());

        // Now refresh the UI.

    }
}
