package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.VideoInfo;
import mooc.spring.malinda.thevideoapp.quries.StoredVideosQuery;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

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

        Log.i(Constants.TAG, "Setting video data in post execute");

        if (videos == null)
        {
            return;
        }

        loadData.getCanShowAllVideos().setVideoData(videos);

        //List<Video> vids = new ArrayList<>();

        //for (VideoDecorator decorator: videos)
        //{
        //    vids.add(decorator.getVideo());
        //}

        //loadData.getAdapter().setVideos(vids);
    }
}
