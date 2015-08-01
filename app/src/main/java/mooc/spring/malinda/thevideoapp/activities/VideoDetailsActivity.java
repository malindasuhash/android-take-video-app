package mooc.spring.malinda.thevideoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoNewOps;
import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;
import mooc.spring.malinda.thevideoapp.utils.Dialoger;

public class VideoDetailsActivity extends ConfigurationHandledActivity<VideoNewOps> implements CanUpdateInputs {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        Log.i(Constants.TAG, "Loading video details");

        super.handleConfiguration(VideoNewOps.class);

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
     * Removes the video locally.
     */
    public void removeVideo(View view)
    {
        Dialoger dialoger = new Dialoger();
        dialoger.show(this, getString(R.string.sure_to_remove), getString(R.string.del_video), mOps);
    }

    /**
     * Adds the video to local store.
     */
    public void addVideo(View view)
    {
       mOps.addVideoToLocalStore();
    }

    /**
     * Stores locally and uploads the video.
     */
    public void uploadVideo(View view)
    {
        //mOps.storeDetailsAnduploadVideo();
        finish();
    }

    /**
     * Sets the video details
     */
    public void setImageInfo(MediaStoreVideo mediaStoreVideo)
    {
        ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setImageBitmap(mediaStoreVideo.getThumbnail());

        // Need to comeback to this. Duration is wrong.
        //((TextView)findViewById(R.id.vidDuration)).setText(mediaStoreVideo.getDuration());
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
    public static Intent makeIntent(Context context, long videoId, Uri videoUri)
    {
        Intent showDetails = new Intent(context, VideoDetailsActivity.class);
        showDetails.putExtra(Constants.VideoId, videoId);
        showDetails.putExtra(Constants.VideoUri, videoUri.toString());

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
