package mooc.spring.malinda.thevideoapp.framework;

import android.app.Activity;

/**
 * The interface to marks the operations that is linked to each activity.
 */
public interface OpsConfig {

    void onConfiguration(Activity activity, boolean firstTimeIn);

}
