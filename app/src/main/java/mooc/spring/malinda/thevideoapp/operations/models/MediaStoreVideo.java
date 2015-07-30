package mooc.spring.malinda.thevideoapp.operations.models;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Wrapper around the Media Store video.
 */
public class MediaStoreVideo {

    private String name;
    private long duration;
    private String mimeType;
    private long dateTaken;
    private String location;
    private Bitmap thumbnail;

    public String getName() {
        return name;
    }

    public String getDuration() {
        float seconds = duration / 1000; // to seconds.
        return Float.toString(seconds) + "s";
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDateTaken() {
        Date dte = new Date(dateTaken);
        String formattedDate = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss").format(dte);

        return formattedDate;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
