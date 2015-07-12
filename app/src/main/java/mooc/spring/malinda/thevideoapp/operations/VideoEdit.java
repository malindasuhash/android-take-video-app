package mooc.spring.malinda.thevideoapp.operations;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import java.lang.ref.WeakReference;
import java.util.List;

import mooc.spring.malinda.thevideoapp.R;
import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.framework.OpsConfig;
import mooc.spring.malinda.thevideoapp.storage.VideoDiaryContract;
import mooc.spring.malinda.thevideoapp.utils.Toaster;

public class VideoEdit implements OpsConfig {

    private MediaStoreFacade mMediaStoreFacade;
    private WeakReference<Activity> mActivity;
    private long mVideoId;
    private boolean mVideoExists;
    private String mFilePath;
    private Video mStoredVideo;

    @Override
    public void onConfiguration(Activity activity, boolean firstTimeIn) {
        mActivity = new WeakReference<>(activity);

        if (firstTimeIn)
        {
            mMediaStoreFacade = new MediaStoreFacade(mActivity.get().getApplicationContext());
            mVideoId = (long)mActivity.get().getIntent().getFloatExtra(Constants.VideoId, 0);
            mVideoExists = doesVideoExists();
            setVideoRatings();
        }

        if (mVideoExists)
        {
            // No need to show this as video exists.
            mActivity.get().findViewById(R.id.uploadVideo).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Removed the video from store.
     */
    public void deleteVideo()
    {
        boolean result = mMediaStoreFacade.deleteVideoFromStore(mActivity.get().getApplicationContext(), mVideoId);

        if (result)
        {
            Toaster.Show(mActivity.get(), "Video marked for deletion locally.");
        } else {
            Toaster.Show(mActivity.get(), "Sorry, there was a problem deleting video.");
        }
    }

    public void playVideo()
    {
        Intent intent = mMediaStoreFacade.makePlayVideoIntent(mActivity.get(), Uri.parse(mFilePath));

        if (intent.resolveActivity(mActivity.get().getPackageManager()) != null)
        {
            Log.i(Constants.TAG, "Invoking the intent to play video");
            mActivity.get().startActivity(intent);
        } else {
            Toaster.Show(mActivity.get(), "Sorry, there isn't any app to play back video.");
        }
    }

    private void setVideoRatings()
    {
        // TODO: implement single lookup in the content provider.
        List<Video> videos = mMediaStoreFacade.getVideos(VideoDiaryContract.VideoEntry.CONTENT_URI);

        for (Video v : videos)
        {
            if (v.getVideoId() == mVideoId)
            {
                mStoredVideo = v;
                break;
            }
        }

        setRatingValue();
    }

    /**
     * Check whether the video exists in media store.
     */
    private boolean doesVideoExists()
    {
        Video video = mMediaStoreFacade.getVideoById(mVideoId);

        if (video != null) {
            Toaster.Show(mActivity.get(), "VideoExists!");

            // Get stored information.
            mFilePath = mMediaStoreFacade.getVideoFilePath(mVideoId);

            return true;
        }

        return false;
    }

    private void setRatingValue()
    {
        if (mStoredVideo != null)
        {
            Log.i(Constants.TAG, "Setting the rating");
            ((RatingBar) mActivity.get().findViewById(R.id.ratings)).setRating(mStoredVideo.getRating());
        }
        else
        {
            ((RatingBar) mActivity.get().findViewById(R.id.ratings)).setRating(0);
        }
    }
}
