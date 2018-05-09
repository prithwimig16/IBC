package com.prithwiraj.ibc.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.RequestQueue;

public class Utils {

    private static Utils _singletonObj = null;
    ProgressDialog progressDialog;
    public static RequestQueue volleyRequestQueue;
    public static Utils getInstance() {
        if (_singletonObj == null) {
            _singletonObj = new Utils();
        }
        return _singletonObj;
    }


    public static void consoleLog(Class classobj, String msg) {
//        if (Config.getSharedInstance().debugMode == true) {
//            Log.d(classobj.getCanonicalName(), msg);
//        }

        Log.d(classobj.getCanonicalName(), msg);
    }

    public void hideLoading() {
        try {


            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();

            }
            this.progressDialog = null;
        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
        }
    }
    public void displayLoading(Context context) {

        this.displayLoading(context,"Loading. Please wait...", ProgressDialog.STYLE_SPINNER);

    }

    public void displayLoading(Context context, String message, int style) {

        try {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                return;
            }
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(style);
            this.progressDialog.setMessage(message);

            this.progressDialog.setIndeterminate(false);
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.show();

        } catch (Exception e) {
            Utils.consoleLog(Utils.class, e.getLocalizedMessage());
        }
    }

    public static void alert(String message, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("IBC Office Manager");

        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }
}
