package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.os.AsyncTask;

import java.util.List;

import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.models.VideoInfo;
import mooc.spring.malinda.thevideoapp.quries.StoredVideosQuery;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.L;

/**
 * Task to load the data from local media store.
 */
public class LoadAllVideosTask extends AsyncTask<LoadDataDto, Void, List<VideoInfo>> {

    private LoadDataDto loadData;

    @Override
    protected List<VideoInfo> doInBackground(LoadDataDto... data) {
        loadData = data[0];
        StoredVideosQuery query = new StoredVideosQuery(loadData.getContext().getApplicationContext());
        return query.getAll(VideoDiaryContract.VideoEntry.CONTENT_URI);
    }

    @Override
    protected void onPostExecute(List<VideoInfo> videos) {

        L.logI("Setting video data in post execute");

        if (videos == null)
        {
            return;
        }

        loadData.getCanShowAllVideos().setVideoData(videos);
    }
}
