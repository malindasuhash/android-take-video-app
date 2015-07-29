package mooc.spring.malinda.thevideoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoNewOps;

public class VideoDetailsActivity extends ConfigurationHandledActivity<VideoNewOps> implements CanUpdateInputs {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        Log.i(Constants.TAG, "Loading video details");

        super.handleConfiguration(VideoNewOps.class);

        mOps.loadVideoData(getIntent());

        bindTextChangedEvents();
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

    private void bindTextChangedEvents()
    {
        final VideoDetailsActivity _this = this;

        ((EditText) findViewById(R.id.vid_t)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                _this.mOps.updateInputLengths(VideoNewOps.Input.Title, charSequence.toString(), _this);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ((EditText) findViewById(R.id.des_t)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                _this.mOps.updateInputLengths(VideoNewOps.Input.Description, charSequence.toString(), _this);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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

    @Override
    public void updateTitleLengh(int value) {
        ((TextView)findViewById(R.id.lengthOfTitle)).setText(Integer.toString(value));
    }

    @Override
    public void updateDescLength(int value) {
        ((TextView)findViewById(R.id.desLen)).setText(Integer.toString(value));
    }
}
