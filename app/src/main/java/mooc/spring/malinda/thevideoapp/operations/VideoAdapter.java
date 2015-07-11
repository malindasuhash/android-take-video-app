package mooc.spring.malinda.thevideoapp.operations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mooc.spring.malinda.thevideoapp.R;

public class VideoAdapter extends BaseAdapter {

    private final Context mContext;

    private static List<Video> videoList = new ArrayList<>();

    public VideoAdapter(Context context) {
        super();
        mContext = context;
    }

    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        Video video = videoList.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =
                    mInflater.inflate(R.layout.video_display_item,null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.videoName);
        titleText.setText(video.getName());

        TextView ratings = (TextView) convertView.findViewById(R.id.ratings);
        ratings.setText(Float.toString(video.getRating()));

        TextView ref = (TextView) convertView.findViewById(R.id.vidref);
        ref.setText(Float.toString(video.getVideoId()));

        return convertView;
    }

    /**
     * Adds a Video to the Adapter and notify the change.
     */
    public void add(Video video) {
        videoList.add(video);
        notifyDataSetChanged();
    }

    /**
     * Removes a Video from the Adapter and notify the change.
     */
    public void remove(Video video) {
        videoList.remove(video);
        notifyDataSetChanged();
    }

    /**
     * Get the List of Videos from Adapter.
     */
    public List<Video> getVideos() {
        return videoList;
    }

    /**
     * Set the Adapter to list of Videos.
     */
    public void setVideos(List<Video> videos) {
        this.videoList = videos;
        notifyDataSetChanged();
    }

    /**
     * Get the no of videos in adapter.
     */
    public int getCount() {
        if (videoList == null)
        {
            return 0;
        }
        return videoList.size();
    }

    /**
     * Get video from a given position.
     */
    public Video getItem(int position) {
        return videoList.get(position);
    }

    /**
     * Get Id of video from a given position.
     */
    public long getItemId(int position) {
        return position;
    }
}
