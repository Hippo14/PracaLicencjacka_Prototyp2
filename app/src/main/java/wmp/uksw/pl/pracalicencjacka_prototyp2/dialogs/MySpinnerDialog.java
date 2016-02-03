package wmp.uksw.pl.pracalicencjacka_prototyp2.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by MSI on 2016-02-03.
 */
public class MySpinnerDialog extends DialogFragment {

    public MySpinnerDialog() {
        // use empty constructors. If something is needed use onCreate's
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        this.setStyle(STYLE_NO_TITLE, getTheme()); // You can use styles or inflate a view
        dialog.setMessage("Spinning.."); // set your messages if not inflated from XML

        dialog.setCancelable(false);

        return dialog;
    }
}