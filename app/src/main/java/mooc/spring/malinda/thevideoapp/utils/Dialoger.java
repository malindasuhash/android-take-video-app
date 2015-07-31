package mooc.spring.malinda.thevideoapp.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import mooc.spring.malinda.thevideoapp.R;

public class Dialoger {

    public void show(Context context, String message, String title, final DialogInfo dialogInfo)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInfo.whenYesClicked();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInfo.whenCancelClicked();
                    }
        });

        builder.create().show();
    }
}
