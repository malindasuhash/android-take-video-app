package mooc.spring.malinda.thevideoapp.commands;

import android.content.Context;
import android.content.Intent;

import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.services.NewVideoStoreOrRemoveService;
import mooc.spring.malinda.thevideoapp.utils.L;

public class AddVideoToLocalStoreCommand {

    /**
     * Adds the video to local store.
     */
    public VideoEx addVideo(Context context, VideoEx video)
    {
        L.logI("Adding video to store.");

        Intent intent = NewVideoStoreOrRemoveService.makeAddVideoIntent(context, video);

        L.logI("Starting intent to add video");
        context.startService(intent);

        return video;
    }
}
