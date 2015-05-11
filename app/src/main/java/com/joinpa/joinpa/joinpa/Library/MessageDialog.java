package com.joinpa.joinpa.joinpa.Library;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by taweesoft on 11/5/2558.
 */
public class MessageDialog {
    private Context context;
    private int icon;
    private String message;
    private String title;

    public MessageDialog(Context context,int icon,String title,String message){
        this.context = context;
        this.icon = icon;
        this.title = title;
        this.message = message;
    }

    public void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setIcon(icon);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }
}
