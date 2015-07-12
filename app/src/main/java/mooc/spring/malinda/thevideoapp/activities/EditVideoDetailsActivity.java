package mooc.spring.malinda.thevideoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoEdit;

public class EditVideoDetailsActivity extends ConfigurationHandledActivity<VideoEdit> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video_details);
        super.handleConfiguration(VideoEdit.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_video_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
