package mooc.spring.malinda.thevideoapp.framework;

import android.app.Activity;
import android.util.Log;

/**
 * Simplification of the original configuration handling code developed
 * by prof. Douglas.
 */
public class ConfigurationHandledActivity<T extends OpsConfig> extends Activity {

    private final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), Constants.TAG);

    public T mOps;

    public void handleConfiguration(Class<T> opsType)
    {
        if (mRetainedFragmentManager.firstTimeIn())
        {
            Log.i(Constants.TAG, "First time coming in, initialising operations");

            init(opsType);
            this.mOps.onConfiguration(this, true);
        }
        else
        {
            Log.i(Constants.TAG, "Reloading state from fragment manager, reinitialising operations");
            this.mOps = mRetainedFragmentManager.get(Constants.VideoOperations);
            if (this.mOps == null) {
               init(opsType);
            }
            this.mOps.onConfiguration(this, false);
        }
    }

    private void init(Class<T> opsType) {
        try {
            this.mOps = opsType.newInstance();
            mRetainedFragmentManager.put(Constants.VideoOperations, this.mOps);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
