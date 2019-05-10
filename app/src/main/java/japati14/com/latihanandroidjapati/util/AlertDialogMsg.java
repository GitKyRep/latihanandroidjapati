package japati14.com.latihanandroidjapati.util;

import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogMsg {
    public static void messageDialog(Context context, String title, String isi, String bt_ok, String bt_cancel, final Runnable if_ok, final Runnable if_cancel ) {

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(isi);

        alert.setPositiveButton( bt_ok.toUpperCase(),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(if_ok!=null) if_ok.run();

                    }
                });

        if(bt_cancel!=null){
            alert.setNegativeButton( bt_cancel.toUpperCase(),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            if(if_cancel!=null) if_cancel.run();

                        }
                    });
        }

        alert.show();

    }
}
