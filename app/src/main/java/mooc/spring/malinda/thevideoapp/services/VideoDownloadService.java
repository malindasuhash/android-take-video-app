package mooc.spring.malinda.thevideoapp.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoHandler;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.Toaster;
import retrofit.client.Response;

public class VideoDownloadService extends IntentService {

    private static final int NOTIFICATION_ID = 1;

    public VideoDownloadService() {
        super("VideoDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            long videoId = intent.getLongExtra(Constants.VideoId, 0);
            long oldVideoId = intent.getLongExtra(Constants.OldVideoId, 0);

            if (videoId == 0)
            {
                Log.i(Constants.TAG, "The intent does not contain a video id.");
                return;
            }

            Log.i(Constants.TAG, "The server video id is " + videoId);
            Log.i(Constants.TAG, "Old video id is " + oldVideoId);

            startNotification();
            VideoHandler handler = new VideoHandler();
            Response response = handler.downloadVideo(videoId);

            Log.i(Constants.TAG, "Storing the downloaded video");
            storeTheReceivedFile(this.getApplicationContext(), response, Constants.Video + videoId + ".mp4", oldVideoId);

            Toaster.Show(this, "Requested file has been downloaded now.");

            // Try to add to media store

        }
    }

    private File storeTheReceivedFile(Context context, Response response, String videoName, long oldVideoId)
    {
        // Try to get the File from the Directory where the Video
        // is to be stored.
        final File file =
                getVideoStorageDir(videoName);
        if (file != null) {
            try {
                // Get the InputStream from the Response.
                final InputStream inputStream =
                        response.getBody().in();

                // Get the OutputStream to the file
                // where Video data is to be written.
                final OutputStream outputStream =
                        new FileOutputStream(file);

                // Write the Video data to the File.
                IOUtils.copy(inputStream,
                        outputStream);

                // Close the streams to free the Resources used by the
                // stream.
                outputStream.close();
                inputStream.close();

                // Always notify the MediaScanners after Downloading
                // the Video, so that it is immediately available to
                // the user.
                notifyMediaScanners(context, file, oldVideoId);


                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void notifyMediaScanners(final Context context,
                                            File videoFile, final long oldVideoId) {
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile
                (context,
                        new String[]{videoFile.getAbsolutePath()},
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path,
                                                        Uri uri) {
                                Log.i(Constants.TAG, "Once added, the returned url " + uri.toString());

                                // Update my content provider to use the new local video id.
                                Log.i(Constants.TAG, "Last segment " + uri.getLastPathSegment());
                                long last = Long.parseLong(uri.getLastPathSegment());

                                ContentValues values = new ContentValues();
                                values.put(Constants.NewVideoId, last);
                                values.put(Constants.OldVideoId, oldVideoId);

                                Log.i(Constants.TAG, "Updating local record.");
                                context.getApplicationContext()
                                        .getContentResolver()
                                        .update(VideoDiaryContract.VideoEntry.CONTENT_URI, values, null,null);

                                finishNotification("Finished downloading!");
                            }
                        });

        Log.i(Constants.TAG, "download video path " + videoFile.toString());
    }

    private static File getVideoStorageDir(String videoName) {
        //Check to see if external SDCard is mounted or not.
        if (isExternalStorageWritable()) {
            // Create a path where we will place our video in the
            // user's public Downloads directory. Note that you should be
            // careful about what you place here, since the user often
            // manages these files.
            final File path =
                    Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS);
            final File file = new File(path,
                    videoName);
            // Make sure the Downloads directory exists.
            path.mkdirs();
            return file;
        } else {
            return null;
        }
    }

    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals
                (Environment.getExternalStorageState());
    }

    /**
     * Starts the Notification to show the progress of video upload.
     */
    private void startNotification() {
        // Gets access to the Android Notification Service.
        NotificationManager mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Video Upload")
                        .setContentText("Upload in progress")
                        .setSmallIcon(android.R.drawable.stat_sys_download)
                        .setTicker("Uploading video")
                        .setProgress(0,
                                0,
                                true);

        // Build and issue the notification.
        mNotifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());
    }

    private  void finishNotification(String status) {
        // When the loop is finished, updates the notification.
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(this)
                .setContentTitle(status)
                // Removes the progress bar.
                .setProgress (0,
                        0,
                        false)
                .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setContentText("")
                .setTicker(status);

        // Build the Notification with the given
        // Notification Id.
        NotificationManager mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(NOTIFICATION_ID,
                mBuilder.build());
    }

}
