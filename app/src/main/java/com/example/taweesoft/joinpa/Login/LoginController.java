package com.example.taweesoft.joinpa.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.LoginState.LoginState;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class LoginController {
    private LoginModel model;
    private LoginActivity loginActivity;
    public LoginController(LoginActivity loginActivity,LoginModel model){
        this.model = model;
        this.loginActivity = loginActivity;
        model.addObserver(loginActivity);
        loginActivity.setLoginAction(new LoginEvent(loginActivity.getAlertDialog()));
    }

    public void doLogin(){
        String username = loginActivity.getUsername();
        String password = loginActivity.getPassword();
        model.doLogin(username,password);
    }

    /**
     * Login event
     */
    class LoginEvent implements View.OnClickListener {
        private AlertDialog dialog;
        public LoginEvent(AlertDialog dialog){
            this.dialog = dialog;
        }
        public void onClick(View v){
            dialog.setMessage("Logging in...");
            dialog.setCancelable(false);
            dialog.setOnCancelListener(new DismissLoginDialogEvent());
            dialog.show();
            doLogin();
        }
    }

    class DismissLoginDialogEvent implements DialogInterface.OnCancelListener{
        public void onCancel(DialogInterface dialog){
            model.getState().performed(loginActivity);
        }
    }

    public void setState(LoginState state){
        model.setState(state);
    }
}
