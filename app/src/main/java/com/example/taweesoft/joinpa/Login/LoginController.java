package com.example.taweesoft.joinpa.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginState.LoginState;

/**
 * Login controller.
 */
public class LoginController {
    private LoginModel model;
    private LoginDialog loginDialog;

    /**
     * Constructor.
     * @param loginDialog
     * @param model
     */
    public LoginController(LoginDialog loginDialog,LoginModel model){
        this.model = model;
        this.loginDialog = loginDialog;
        model.addObserver(loginDialog); //Add observer to model.

        /*Initial action for buttons.*/
        loginDialog.setLoginAction(new LoginEvent(loginDialog.getAlertDialog()));
        loginDialog.setSignUpAction(new SignUpAction());
    }

    /**
     * Call sign in.
     */
    public void doSignIn(){
        String username = loginDialog.getUsername();
        String password = loginDialog.getPassword();
        model.doSignIn(username,password);
    }

    /**
     * Call sign up.
     */
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

    /**
     * Action when dismiss the message dialog, depend on state.
     */
    class DismissLoginDialogEvent implements DialogInterface.OnCancelListener{
        public void onCancel(DialogInterface dialog){
            model.getState().performed(loginDialog.getActivity());
        }
    }

    /**
     * Sign up action.
     */
    class SignUpAction implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            loginDialog.showMessage("Checking...", Resources.CHECKING);
            String username = loginDialog.getUsername();
            String password = loginDialog.getPassword();
            model.doSignUp(username,password);
        }
    }

    /**
     * Show message dialog and do login.
     * @param dialog
     */
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
