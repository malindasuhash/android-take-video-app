package mooc.spring.malinda.thevideoapp.operations.tasks;

import android.os.AsyncTask;

/**
 * Loads details about the video that was just recorded.
 */
public class NewVideoDetailsTask extends AsyncTask<Long, Void, Void> {

    @Override
    protected Void doInBackground(Long... longs) {
        long videoId = longs[0];

        return null;
    }
}
