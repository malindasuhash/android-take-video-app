package mooc.spring.malinda.thevideoapp.commands;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import mooc.spring.malinda.thevideoapp.services.NewVideoStoreOrRemoveService;
import mooc.spring.malinda.thevideoapp.utils.L;

public class RemoveVideoLocalCommand {

    public void RemoveVideo(Uri videoUri, Context context)
    {
        L.logI("Creates an intent to remove a video.");
        Intent removeVideoIntent = NewVideoStoreOrRemoveService.makeRemoveVideoIntent(context, videoUri);
        context.startService(removeVideoIntent);
    }
}
