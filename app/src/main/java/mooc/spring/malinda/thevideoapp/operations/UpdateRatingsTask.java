package mooc.spring.malinda.thevideoapp.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;

public class UpdateRatingsTask extends AsyncTask<RatingInfo, Void, Void> {

    private VideoHandler handler = new VideoHandler();
    private Context context;

    public UpdateRatingsTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(RatingInfo... ratings) {
        handler.updateNewRating(ratings[0].getServerVideoId(), ratings[0].getNewRatings());

        Log.i(Constants.TAG, "Updating the local rating");

        // Bit hacky, this has to be done in the content provider through urimatcher, (for later)
        ContentValues values = new ContentValues();
        values.put(Constants.NewVideoId, ratings[0].getOldVideoId());
        values.put(Constants.ServerRating, ratings[0].getNewRatings());
        values.put(Constants.OldVideoId, ratings[0].getOldVideoId());

        context.getContentResolver().update(VideoDiaryContract.VideoEntry.CONTENT_URI, values, null, null);

        BroadcastRefresh.broadcastRefresh(context);
        return null;
    }

}
