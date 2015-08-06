package mooc.spring.malinda.thevideoapp.services.uploadSteps;

import mooc.spring.malinda.thevideoapp.operations.VideoEx;

public interface Step {
    boolean execute(VideoEx video);
}
