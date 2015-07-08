package mooc.spring.malinda.thevideoapp.operations;

public class Video {

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public String getContentType() {
        return contentType;
    }

    private String name;
    private long duration;
    private String contentType;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    private long videoId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;

    public Video(String name, long duration, String contentType)
    {

        this.name = name;
        this.duration = duration;
        this.contentType = contentType;
    }
}
