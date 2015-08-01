package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.activities.EditVideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.activities.MainActivity;
import mooc.spring.malinda.thevideoapp.activities.VideoDetailsActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.operations.dtos.LoadDataDto;
import mooc.spring.malinda.thevideoapp.operations.tasks.LoadAllVideosTask;
import mooc.spring.malinda.thevideoapp.utils.L;

public class VideoOps implements OpsConfig, CanShowAllVideos {

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private WeakReference<Activity> mActivity;
    private WeakReference<ListView> mVideoList;
    private VideoAdapter mAdapter;
    private boolean hasVideosToShow = false;

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

            showVideoDetails(videoId, videoUri);
        }
        else {
            L.logI("Looks like the video was cancelled by user.");
        }
    }

    /**
     * Get details for the the video.
     */
    public void showDetailsForViewIndex(int i, View view)
    {
        float videoId = Float.parseFloat(((TextView) view.findViewById(R.id.vidref)).getText().toString());

        Log.i(Constants.TAG, "Request for details for index " + i + " videoId " + videoId);
        Intent intent = EditVideoDetailsActivity.makeIntentToEditVideo(getActivity().getApplicationContext(), videoId);
        getActivity().startActivity(intent);
    }

    /**
     * Called on configuration changes.
     */
    public void onConfiguration(Activity activity,
                                boolean firstTimeIn) {

        mActivity = new WeakReference<>(activity);
        mVideoList = new WeakReference<>((ListView)getActivity().findViewById(R.id.listView));

        if (firstTimeIn) {
            mAdapter = new VideoAdapter(getActivity().getApplicationContext());
            getVideoList();
        }

        mVideoList.get().setAdapter(mAdapter);
        setLoadState();
    }

    private void showVideoDetails(long id, Uri videoUri)
    {
        L.logI("Showing the video details");

        Intent showDetails = VideoDetailsActivity.makeIntent(getActivity().getApplicationContext(), id, videoUri);
        getActivity().startActivity(showDetails);
    }

    public void getVideoList()
    {
        LoadAllVideosTask allVideosTask = new LoadAllVideosTask();
        LoadDataDto data = new LoadDataDto();
        data.setContext(getActivity());
        data.setAdapter(mAdapter);
        data.setCanShowAllVideos(this);

        allVideosTask.execute(data);

        Log.i(Constants.TAG, "Loading videos, through the task.");
    }

    private MainActivity getActivity()
    {
        return (MainActivity)mActivity.get();
    }

    @Override
    public void setVideoData(List<VideoEx> videoData) {
        L.logI("Callback from loading video data. " + videoData.size());

        hasVideosToShow = (videoData.size() > 0);
        mAdapter.setVideos(videoData);
        setLoadState();
    }

    private void setLoadState() {
        if (!hasVideosToShow)
        {
            L.logI("There are no videos to show!");
            getActivity().noVideosFound();
        } else
        {
            L.logI("Videos found!");
            getActivity().showVideoList();
        }
    }
}
