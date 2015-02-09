package com.example.nash.modernui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class AlertDialogFragment extends DialogFragment{
    private static final String MOMA_SITE_URL = "http://www.moma.org";
    public static AlertDialogFragment newInstance() {
        return new AlertDialogFragment();
    }

    // Build a new AlertDialog using AlertDialog.builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Inspired the works of Piet Mondrian and Ben Nicholson. Visit MOMA website to know more.")
                .setTitle("More Information")
                //User cannot dismiss the dialog by hitting the back button
                .setCancelable(true)

                // Set up the no button
                .setNegativeButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Kill the dialog simply no need to do anything else
                                }
                            }
                        )

                // Set up the positive button
                .setPositiveButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent momaSiteIntent = new Intent("android.intent.action.VIEW", Uri.parse(MOMA_SITE_URL));
                                    startActivity(momaSiteIntent);
                                }
                            }

                        ).create();
    }

}
