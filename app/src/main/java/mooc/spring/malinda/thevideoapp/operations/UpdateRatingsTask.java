package mooc.spring.malinda.thevideoapp.operations;

import android.os.AsyncTask;

public class UpdateRatingsTask extends AsyncTask<RatingInfo, Void, Void> {

    private VideoHandler handler = new VideoHandler();

    @Override
    protected Void doInBackground(RatingInfo... ratings) {
        handler.updateNewRating(ratings[0].getServerVideoId(), ratings[0].getNewRatings());
        return null;
    }

}
