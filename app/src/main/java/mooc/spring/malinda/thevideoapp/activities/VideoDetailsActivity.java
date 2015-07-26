package mooc.spring.malinda.thevideoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoDetailsOps;

public class VideoDetailsActivity extends ConfigurationHandledActivity<VideoDetailsOps> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        Log.i(Constants.TAG, "Loading video details");

        super.handleConfiguration(VideoDetailsOps.class);

        mOps.loadVideoData(getIntent());
    }

    /**
     * Plays the video using an implicit intent.
     */
    public void playVideo(View view)
    {
        mOps.playVideo();
    }

    /**
     * Stores locally and uploads the video.
     */
    public void uploadVideo(View view)
    {
        mOps.storeDetailsAnduploadVideo();
        finish();
    }

    public void ratingChanged(View view)
    {
        mOps.ratingChanged();
    }

    /**
     * Creates an explicit intent for the caller to use this activity.
     */
    public static Intent makeIntent(Context context, long videoId)
    {
        Intent showDetails = new Intent(context, VideoDetailsActivity.class);
        showDetails = showDetails.putExtra(Constants.VideoId, videoId);

        return showDetails;
    }

}
