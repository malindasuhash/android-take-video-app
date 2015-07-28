package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.content.Context;
import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.quries.VideoMediaStoreQuery;
import mooc.spring.malinda.thevideoapp.utils.L;

/**
 * Loads details about the video that was just recorded.
 */
public class NewVideoDetailsTask extends AsyncTask<LoadDataDto, Void, MediaStoreVideo> {

    @Override
    protected MediaStoreVideo doInBackground(LoadDataDto... data) {
        long videoId = data[0].getVideoId();
        Context context = data[0].getContext();

        VideoMediaStoreQuery query = new VideoMediaStoreQuery(context);

        L.logI("Looking up video details by " + videoId);
        return query.getVideoById(videoId);
    }
}
