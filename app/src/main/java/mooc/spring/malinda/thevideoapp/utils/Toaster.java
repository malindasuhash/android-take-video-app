package mooc.spring.malinda.thevideoapp.utils;


import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public static void Show(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
