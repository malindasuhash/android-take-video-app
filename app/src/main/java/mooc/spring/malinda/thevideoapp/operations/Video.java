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

    public Video(String name, long duration, String contentType)
    {

        this.name = name;
        this.duration = duration;
        this.contentType = contentType;
    }
}
