package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;

public class LoadData {
    private Context context;
    private VideoAdapter adapter;

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
}
