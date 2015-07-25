package mooc.spring.malinda.thevideoapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import mooc.spring.malinda.thevideoapp.utils.L;


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent showSource = new Intent(Intent.ACTION_VIEW);
            showSource.setData(Uri.parse("https://github.com/malindasuhash/android-take-video-app"));
            startActivity(showSource); // No need to check
            return true;
        }

        if (id == R.id.add_video)
        {
            L.logI("Ready to take a video. Creating intent to open built in video app.");
            this.mOps.takeVideo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows the list of videos.
     */
    public void showVideoList()
    {
        findViewById(R.id.loading).setVisibility(View.GONE);
        findViewById(R.id.novideosImg).setVisibility(View.GONE);
        findViewById(R.id.vovidsmsg).setVisibility(View.GONE);
        findViewById(R.id.listView).setVisibility(View.VISIBLE);
    }

    /**
     * No videos found, therefore showing the no videos image and text.
     */
    public void noVideosFound()
    {
        findViewById(R.id.loading).setVisibility(View.GONE);
        findViewById(R.id.novideosImg).setVisibility(View.VISIBLE);
        findViewById(R.id.vovidsmsg).setVisibility(View.VISIBLE);
        findViewById(R.id.listView).setVisibility(View.GONE);
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
                .getInstance(this.getApplicationContext())
                .unregisterReceiver(refreshBroadcastReceiver);
    }

    private void registerForBroadcast()
    {
        LocalBroadcastManager
                .getInstance(this.getApplicationContext())
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
