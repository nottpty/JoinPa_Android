package com.example.taweesoft.joinpa.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginState.FailedState;
import com.example.taweesoft.joinpa.Login.LoginState.LoginState;
import com.example.taweesoft.joinpa.Login.LoginState.SuccessState;
import com.example.taweesoft.joinpa.GCMIntentService;
import com.example.taweesoft.joinpa.R;
import com.example.taweesoft.joinpa.Register.RegisterDialogController;
import com.google.android.gcm.GCMRegistrar;

import java.util.Observable;
import java.util.Observer;


public class LoginDialog implements Observer {
    private Owner owner = null;
    private final LoginState successState = new SuccessState();
    private final LoginState failedState = new FailedState();
    private LoginController controller;
    private Activity activity;
    private EditText txt_username,txt_password;
    private TextView txt_signUp,txt_signIn,txt_msg1,txt_msg2;
    private AlertDialog alertDialog;
    private Dialog dialog;
    public LoginDialog(Activity activity){
        this.activity = activity;
        alertDialog = new AlertDialog.Builder(activity).create();
        initComponents();
    }


    public void initComponents(){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Hide the action bar.
        dialog.setContentView(R.layout.activity_login);
        txt_username = (EditText)dialog.findViewById(R.id.txt_username);
        txt_password=  (EditText)dialog.findViewById(R.id.txt_password);
        txt_signUp = (TextView)dialog.findViewById(R.id.txt_signUp);
        txt_signIn = (TextView)dialog.findViewById(R.id.txt_signIn);
        txt_msg1 = (TextView)dialog.findViewById(R.id.txt_msg1);
        txt_msg2 = (TextView)dialog.findViewById(R.id.txt_msg2);
        LoginModel model = new LoginModel();
        controller = new LoginController(this,model);
    }

    public void openDialog(){
        dialog.show();
    }

    /**
     * Set action for login button.
     */
    public void setLoginAction( View.OnClickListener action){
        txt_signIn.setOnClickListener(action);
    }

    public void setSignUpAction (View.OnClickListener action){
        txt_signUp.setOnClickListener(action);
    }


    /**
     * Get Owner.
     * @return owner.
     */
    public Owner getOwner() {
        return owner;
    }


    public String getUsername(){
        return txt_username.getText().toString();
    }

    public String getPassword(){
        return txt_password.getText().toString();
    }


    @Override
    public void update(Observable observable,final Object data) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(data == null){
                    alertDialog.setMessage("Wrong username or password");
                    controller.setState(failedState);
                    alertDialog.setCancelable(true);
                    return;
                }
                if(data.getClass() == Owner.class){
                    Resources.owner = (Owner)data;
                    Resources.password = getPassword();
                    alertDialog.setMessage("Logged in");
                    controller.setState(successState);
                    controller.executeState();
                    alertDialog.setCancelable(true);
                    return;
                }
                if(data.getClass() == String.class){
                    String msg = (String)data;
                    String[] dataArr = msg.split("@@");
                    int respondCode = Integer.parseInt(dataArr[0]);
                    msg = dataArr[1];
                    txt_username.setText("");
                    txt_password.setText("");
                    showMessage(msg,respondCode);

                }
            }
        });


    }

    public void showMessage(String message,int respondCode){
        txt_msg1.setText(message);
        txt_msg2.setText(message);
        txt_msg1.setVisibility(View.VISIBLE);
        txt_msg2.setVisibility(View.VISIBLE);
        int color = 0;
        if(respondCode == Resources.CHECKING){
            color = 0xffe591ac; // Pink
        }else if(respondCode == Resources.REGIS_SUCCESS){
            color = 0xff61ce1b; // Green
        }else{
            color = 0xffff0c1a; // Red
        }
        txt_msg1.setTextColor(color);
        txt_msg2.setTextColor(color);

    }

    public AlertDialog getAlertDialog(){
        return alertDialog;
    }

    public Activity getActivity(){
        return activity;
    }

    public LoginController getController(){
        return controller;
    }

    public void setUsernamePassword(String username,String password){
        txt_username.setText(username);
        txt_password.setText(password);
    }

}
