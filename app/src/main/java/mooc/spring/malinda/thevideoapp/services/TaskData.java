package mooc.spring.malinda.thevideoapp.services;

import android.content.Context;

import mooc.spring.malinda.thevideoapp.operations.Video;

public class TaskData {
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Video video;
    private Context context;
}
