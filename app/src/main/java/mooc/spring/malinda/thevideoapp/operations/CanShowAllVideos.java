package mooc.spring.malinda.thevideoapp.operations;

import java.util.List;

import mooc.spring.malinda.thevideoapp.operations.models.VideoInfo;

/**
 * Handles callback when the loading of videos
 * from content provider is complete.
 */
public interface CanShowAllVideos {

    void setVideoData(List<VideoInfo> videoData);
}
