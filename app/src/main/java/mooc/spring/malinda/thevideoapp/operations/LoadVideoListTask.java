package mooc.spring.malinda.thevideoapp.operations;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

/**
 * Task to load the data from local media store.
 */
public class LoadVideoListTask extends AsyncTask<LoadData, Void, List<VideoDecorator>> {

    private LoadData loadData;

    @Override
    protected List<VideoDecorator> doInBackground(LoadData... data) {
        loadData = data[0];
        MediaStoreFacade facade = new MediaStoreFacade(loadData.getContext().getApplicationContext());
        return facade.getVideos(VideoDiaryContract.VideoEntry.CONTENT_URI);
    }

    @Override
    protected void onPostExecute(List<VideoDecorator> videos) {
        super.onPostExecute(videos);
        Log.i(Constants.TAG, "Setting video data in post execute");

        if (videos == null)
        {
            return;
        }

        List<Video> vids = new ArrayList<>();

        for (VideoDecorator decorator: videos)
        {
            vids.add(decorator.getVideo());
        }

        loadData.getAdapter().setVideos(vids);
    }
}
