package mooc.spring.malinda.thevideoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoEdit;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class EditVideoDetailsActivity extends ConfigurationHandledActivity<VideoEdit> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video_details);
        super.handleConfiguration(VideoEdit.class);
        setupRatingCallback();
    }

    /**
     * Plays the selected video.
     */
    public void playVideo(View view)
    {
        this.mOps.playVideo();
    }

    /**
     * Removes the given video.
     */
    public void deleteVideo(View view)
    {
        this.mOps.deleteVideo();
        finish();
    }

    private void setupRatingCallback()
    {
        ((RatingBar) findViewById(R.id.ratings)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (!b) return;
                updateRating(ratingBar);
            }
        });
    }

    /**
     * Updates the ratings in the server.
     */
    public void updateRating(View view)
    {
       Log.i(Constants.TAG, "Updating rating.");
       this.mOps.updateRatingsOnServer();
    }

    /**
     * Downloads the video from server.
     */
    public void downloadVideo(View view)
    {
        this.mOps.downloadVideoFromServer();
        Toaster.Show(this, "Download started");
        finish();
    }

    /**
     * Creates the intent to view the details of the video.
     */
    public static Intent makeIntentToEditVideo(Context context, float videoId)
    {
        Intent intent = new Intent(context, EditVideoDetailsActivity.class);
        intent.putExtra(Constants.VideoId, videoId);

        return intent;
    }
}
