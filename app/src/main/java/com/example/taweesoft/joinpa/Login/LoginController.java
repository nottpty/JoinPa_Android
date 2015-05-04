package com.example.taweesoft.joinpa.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginState.LoginState;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class LoginController {
    private LoginModel model;
    private LoginDialog loginDialog;
    public LoginController(LoginDialog loginDialog,LoginModel model){
        this.model = model;
        this.loginDialog = loginDialog;
        model.addObserver(loginDialog);
        loginDialog.setLoginAction(new LoginEvent(loginDialog.getAlertDialog()));
        loginDialog.setSignUpAction(new SignUpAction());
    }

    public void doSignIn(){
        String username = loginDialog.getUsername();
        String password = loginDialog.getPassword();
        model.doSignIn(username,password);
    }

    public void doSignUp(){
        String username = loginDialog.getUsername();
        String password = loginDialog.getPassword();
        model.doSignUp(username,password);
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
           login(dialog);
        }
    }

    class DismissLoginDialogEvent implements DialogInterface.OnCancelListener{
        public void onCancel(DialogInterface dialog){
            model.getState().performed(loginDialog.getActivity());
        }
    }

    class SignUpAction implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            loginDialog.showMessage("Checking...", Resources.CHECKING);
            String username = loginDialog.getUsername();
            String password = loginDialog.getPassword();
            model.doSignUp(username,password);
        }
    }

    public void login(AlertDialog dialog){
        dialog.setMessage("Logging in...");
        dialog.setCancelable(false);
        dialog.setOnCancelListener(new DismissLoginDialogEvent());
        dialog.show();
        doSignIn();
    }
    public void setState(LoginState state){
        model.setState(state);
    }

    public void executeState(){
        model.getState().performed(loginDialog.getActivity());
    }
}
