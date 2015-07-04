package mooc.spring.malinda.thevideoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.RetainedFragmentManager;
import mooc.spring.malinda.thevideoapp.operations.VideoOps;


public class MainActivity extends Activity {

    private final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), Constants.TAG);

    private VideoOps mVideoOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleConfiguration();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
     * Invokes the intent to open the build in camera to take the video.
     */
    public void takeVideo(View view)
    {
        this.mVideoOps.takeVideo();
    }

    /**
        Delegates the call to operations to validate and do the bits.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mVideoOps.whenTakingVideoCompletes(requestCode, resultCode, data);
    }

    /**
        Simplification of the configuration handling on rotation change.
     */
    private void handleConfiguration()
    {
        if (mRetainedFragmentManager.firstTimeIn())
        {
            Log.i(Constants.TAG, "First time coming in, initialising operations");
            VideoOps ops = new VideoOps();
            mRetainedFragmentManager.put(Constants.VideoOperations, ops);
            this.mVideoOps = ops;
            this.mVideoOps.onConfiguration(this, true);
        }
        else
        {
            Log.i(Constants.TAG, "Reloading state from fragment manager, reinitialising operations");
            this.mVideoOps = mRetainedFragmentManager.get(Constants.VideoOperations);
            this.mVideoOps.onConfiguration(this, false);
        }
    }
}
