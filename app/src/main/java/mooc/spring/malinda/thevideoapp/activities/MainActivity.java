package mooc.spring.malinda.thevideoapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.ConfigurationHandledActivity;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoOps;
import mooc.spring.malinda.thevideoapp.utils.Toaster;


public class MainActivity extends ConfigurationHandledActivity<VideoOps> {

    private RefreshBroadcastReceiver refreshBroadcastReceiver = new RefreshBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListViewClick();
        super.handleConfiguration(VideoOps.class);
        registerForBroadcast();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Refresh the UI.
     */
    public void refreshVideoList(View view)
    {
        Toaster.Show(this, "... Refreshing ...");
        mOps.getVideoList();
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

    public void setListViewClick()
    {
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mOps.showDetailsForViewIndex(i, view);
            }
        });
    }
    /**
     * Invokes the intent to open the build in camera to take the video.
     */
    public void takeVideo(View view)
    {
        this.mOps.takeVideo();
    }

    /**
        Delegates the call to operations to validate and do the bits.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mOps.whenTakingVideoCompletes(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregisters broadcast
        LocalBroadcastManager
                .getInstance(this)
                .unregisterReceiver(refreshBroadcastReceiver);
    }

    private void registerForBroadcast()
    {
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(refreshBroadcastReceiver,
                        new IntentFilter(Constants.RefreshBoardcast));
    }

    /**
     * Simple broadcast receiver to reload the video list.
     */
    public class RefreshBroadcastReceiver extends BroadcastReceiver {
        public RefreshBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Constants.TAG, "Request to refresh video list received.");
            mOps.getVideoList();
        }
    }
}
