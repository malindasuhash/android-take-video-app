package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;
import android.os.AsyncTask;

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

        BroadcastRefresh.broadcastRefresh(context);
        return null;
    }

}
