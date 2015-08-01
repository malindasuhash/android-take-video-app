package mooc.spring.malinda.thevideoapp.commands;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.services.NewVideoStoreOrRemoveService;
import mooc.spring.malinda.thevideoapp.utils.L;

public class AddVideoToLocalStoreCommand {

    /**
     * Adds the video to local store.
     */
    public void addVideo(Context context, String title, String desc, Uri videoUri, MediaStoreVideo mStoredVideo)
    {
        L.logI("Adding video to store.");

        VideoEx video = VideoEx.map(title, desc, videoUri.toString(), mStoredVideo);

        Intent intent = NewVideoStoreOrRemoveService.makeAddVideoIntent(context, video);

        L.logI("Starting intent to add video");
        context.startService(intent);
    }
}
