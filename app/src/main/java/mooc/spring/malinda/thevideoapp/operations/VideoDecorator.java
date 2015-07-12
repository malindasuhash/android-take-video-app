package mooc.spring.malinda.thevideoapp.operations;

public class VideoDecorator {

    private Video video;
    private long serverId;

    public VideoDecorator(Video video)
    {
        this.video = video;
    }

    public Video getVideo() {
        return video;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
}
