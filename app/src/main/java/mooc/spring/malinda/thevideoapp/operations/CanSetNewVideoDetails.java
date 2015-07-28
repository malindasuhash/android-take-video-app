package mooc.spring.malinda.thevideoapp.operations;

import mooc.spring.malinda.thevideoapp.operations.models.MediaStoreVideo;

/**
 * Callback for setting the new video details.
 */
public interface CanSetNewVideoDetails {
    void setNewVideoDetails(MediaStoreVideo mediaStoreVideo);
}
