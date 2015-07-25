package mooc.spring.malinda.thevideoapp.operations.models;

import java.util.Date;

/**
 * Simple Data Transfer Object to store video information.
 */
public class VideoInfo {

    private String title;
    private Date createdDateTime;


    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
