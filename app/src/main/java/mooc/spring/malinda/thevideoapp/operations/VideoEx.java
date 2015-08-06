package mooc.spring.malinda.thevideoapp.operations;

import java.io.Serializable;

import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;

public class VideoEx implements Serializable {

    private String title;
    private String description;
    private long duration;
    private String contentType;
    private String dataUrl;
    private long dateTaken;
    private String reference;
    private boolean uploaded;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public static VideoEx map(String title, String description, String dataUrl, MediaStoreVideo mediaStoreVideo, String reference, boolean uploaded)
    {
        VideoEx video = new VideoEx();
        video.setTitle(title);
        video.setDescription(description);
        video.setContentType(mediaStoreVideo.getMimeType());
        video.setDateTaken(mediaStoreVideo.getDateTaken());
        video.setDuration(mediaStoreVideo.getDuration());
        video.setDataUrl(dataUrl);
        video.setReference(reference);
        video.setUploaded(uploaded);

        return video;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public void setUploadedWithStr(String uploaded) {
        this.uploaded = Boolean.getBoolean(uploaded);
    }

    public String getUploadedWithStr() {
        return String.valueOf(this.uploaded);
    }
}
