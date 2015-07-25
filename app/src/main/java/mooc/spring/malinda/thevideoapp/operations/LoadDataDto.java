package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;

public class LoadDataDto {

    private Context context;
    private VideoAdapter adapter;
    private CanShowAllVideos canShowAllVideos;

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
}
