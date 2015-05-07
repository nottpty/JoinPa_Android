package com.example.taweesoft.joinpa.Logout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.example.taweesoft.joinpa.FirstActivity;
import com.example.taweesoft.joinpa.Library.Resources;

/**
 * Log out dialog.
 */
public class LogoutDialog {
    private Activity activity;

    /**
     * Contructor.
     * @param activity
     */
    public LogoutDialog(Activity activity){
        this.activity = activity;
    }

    /**
     * Show log out dialog.
     */
    public void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Log out");
        dialog.setMessage("Log out now ?");
        dialog.setPositiveButton("YES",new LogoutAction());
        dialog.setNegativeButton("NO",null);
        dialog.show();
    }

    /**
     * Action of log out button (confirm).
     */
    class LogoutAction implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            logout();
        }
    }

    /**
     * Do log out and delete auto login file.
     */
    public void logout(){
        Resources.file.delete();
        Intent intent = new Intent(activity,FirstActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
