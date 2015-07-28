package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.content.Context;
import android.os.AsyncTask;

import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.quries.VideoBitmapQuery;
import mooc.spring.malinda.thevideoapp.quries.VideoMediaStoreQuery;
import mooc.spring.malinda.thevideoapp.utils.L;

/**
 * Loads details about the video that was just recorded.
 */
public class NewVideoDetailsTask extends AsyncTask<LoadDataDto, Void, MediaStoreVideo> {

    private LoadDataDto loadDataDto;

    @Override
    protected MediaStoreVideo doInBackground(LoadDataDto... data) {
        loadDataDto = data[0];
        long videoId = loadDataDto.getVideoId();
        Context context = loadDataDto.getContext();

        VideoMediaStoreQuery query = new VideoMediaStoreQuery(context);

        L.logI("Looking up video details by " + videoId);
        MediaStoreVideo video = query.getVideoById(videoId);

        VideoBitmapQuery bitmapQuery = new VideoBitmapQuery(context);

        L.logI("Setting the bitmap");
        video.setThumbnail(bitmapQuery.getBitmap(videoId));

        return video;
    }

    @Override
    protected void onPostExecute(MediaStoreVideo mediaStoreVideo) {
        super.onPostExecute(mediaStoreVideo);
        loadDataDto.getCanSetNewVideoDetails().setNewVideoDetails(mediaStoreVideo);
    }
}
