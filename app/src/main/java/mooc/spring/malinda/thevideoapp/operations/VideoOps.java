package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.List;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.MainActivity;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoOps extends AsyncTask<Void, Void, List<Video>> implements OpsConfig {

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private WeakReference<Activity> mActivity;
    private WeakReference<ListView> mVideoList;
    private VideoAdapter mAdapter;

    /**
     * Uses the build in video application to take the video.
     */
    public void takeVideo()
    {
        Log.i(Constants.TAG, "Making the intent to call build-in camera to take a video.");

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    /**
     * Callback that is invoked when video is taken. Invoked from the onActivityResult.
     */
    public void whenTakingVideoCompletes(int requestCode, int resultCode, Intent data)
    {
        Log.i(Constants.TAG, "Video recording complete, callback received.");

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK)
        {
            Uri videoUri = data.getData();
            Long videoId = ContentUris.parseId(videoUri);
            Log.i(Constants.TAG, "We have a recorded video. Uri is " + videoUri + "; id is " + videoId);

            showVideoDetails(videoId);
        }
        else {
            Toaster.Show(getActivity(), "Sorry looks like the video was cancelled :(");
        }
    }

    /**
     * Get details for the the video.
     */
    public void showDetailsForViewIndex(int i)
    {
        Log.i(Constants.TAG, "Request for details for index " + i);
    }

    public void onConfiguration(Activity activity,
                                boolean firstTimeIn) {
        // Create a WeakReference to the activity.

        mActivity = new WeakReference<>(activity);
        mVideoList = new WeakReference<>((ListView)getActivity().findViewById(R.id.listView));

        if (firstTimeIn) {
            mAdapter = new VideoAdapter(getActivity().getApplicationContext());
            getVideoList();
        }

        mVideoList.get().setAdapter(mAdapter);

    }

    private void showVideoDetails(long id)
    {
        Log.i(Constants.TAG, "Showing the video details");

        Intent showDetails = new Intent(getActivity(), VideoDetailsActivity.class);
        showDetails = showDetails.putExtra("videoId", id);
        getActivity().startActivity(showDetails);
    }

    private void getVideoList()
    {
        this.execute();
        Log.i(Constants.TAG, "Loading videos, through the task.");
    }

    private MainActivity getActivity()
    {
        return (MainActivity)mActivity.get();
    }

    @Override
    protected List<Video> doInBackground(Void... voids) {
        MediaStoreFacade facade = new MediaStoreFacade(getActivity().getApplicationContext());
        return facade.getVideos(VideoDiaryContract.VideoEntry.CONTENT_URI);
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        super.onPostExecute(videos);
        Log.i(Constants.TAG, "Setting video data in post execute");
        mAdapter.setVideos(videos);
    }
}
