package mooc.spring.malinda.thevideoapp.retrofit;

public class VideoMetaDto {

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    private String dataUrl;
    private int videoId;

}
