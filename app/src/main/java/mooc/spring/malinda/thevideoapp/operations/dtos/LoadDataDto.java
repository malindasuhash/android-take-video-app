package mooc.spring.malinda.thevideoapp.operations.dtos;

import android.content.Context;

import mooc.spring.malinda.thevideoapp.operations.CanSetNewVideoDetails;
import mooc.spring.malinda.thevideoapp.operations.CanShowAllVideos;
import mooc.spring.malinda.thevideoapp.operations.VideoAdapter;

public class LoadDataDto {

    private Context context;
    private VideoAdapter adapter;
    private CanShowAllVideos canShowAllVideos;
    private long videoId;
    private CanSetNewVideoDetails canSetNewVideoDetails;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public VideoAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(VideoAdapter adapter) {
        this.adapter = adapter;
    }

    public void setCanShowAllVideos(CanShowAllVideos canShowAllVideos) {
        this.canShowAllVideos = canShowAllVideos;
    }

    public CanShowAllVideos getCanShowAllVideos() {
        return canShowAllVideos;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public CanSetNewVideoDetails getCanSetNewVideoDetails() {
        return canSetNewVideoDetails;
    }

    public void setCanSetNewVideoDetails(CanSetNewVideoDetails canSetNewVideoDetails) {
        this.canSetNewVideoDetails = canSetNewVideoDetails;
    }
}
