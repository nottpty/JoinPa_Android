package com.joinpa.taweesoft.joinpa.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joinpa.taweesoft.joinpa.Library.Owner;
import com.joinpa.taweesoft.joinpa.Library.Resources;
import com.joinpa.taweesoft.joinpa.Login.LoginState.FailedState;
import com.joinpa.taweesoft.joinpa.Login.LoginState.LoginState;
import com.joinpa.taweesoft.joinpa.Login.LoginState.SuccessState;
import com.joinpa.taweesoft.joinpa.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Login dialog (view)
 */
public class LoginDialog implements Observer {
    private Owner owner = null;
    private final LoginState successState = new SuccessState();
    private final LoginState failedState = new FailedState();
    private LoginController controller;
    private Activity activity;
    private EditText txt_username,txt_password;
    private TextView txt_msg1,txt_msg2;
    private Button btn_signIn,btn_signUp;
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
        btn_signUp = (Button)dialog.findViewById(R.id.btn_signUp);
        btn_signIn = (Button)dialog.findViewById(R.id.btn_signIn);
        txt_msg1 = (TextView)dialog.findViewById(R.id.txt_msg1);
        txt_msg2 = (TextView)dialog.findViewById(R.id.txt_msg2);

        /*Create model and controller.*/
        LoginModel model = new LoginModel();
        controller = new LoginController(this,model);
    }

    /**
     * Show dialog.
     */
    public void openDialog(){
        dialog.show();
    }

    /**
     * Set action for login button.
     */
    public void setLoginAction( View.OnClickListener action){
        btn_signIn.setOnClickListener(action);
    }

    /**
     * Set action for sign up button.
     */
    public void setSignUpAction (View.OnClickListener action){
        btn_signUp.setOnClickListener(action);
    }


    /**
     * Get Owner.
     * @return owner.
     */
    public Owner getOwner() {
        return owner;
    }


    public String getUsername(){
        return txt_username.getText().toString().toLowerCase();
    }

    public String getPassword(){
        return txt_password.getText().toString();
    }


    /**
     * Update information.
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable,final Object data) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*Wrong username or password*/
                if(data == null){
                    alertDialog.setMessage("Wrong username or password");
                    controller.setState(failedState); //Set to failed state.
                    alertDialog.setCancelable(true);
                    return;
                }
                /*Correct username and password.*/
                if(data.getClass() == Owner.class){
                    Resources.owner = (Owner)data; //Set owner in Resources.java
                    Resources.password = getPassword();
                    alertDialog.setMessage("Logged in");
                    controller.setState(successState); //Set to success state.
                    controller.executeState(); //Run the state.
                    alertDialog.setCancelable(true); //Can cancel the dialog
                    return;
                }
                /*For register new user.*/
                if(data.getClass() == String.class){
                    String msg = (String)data;
                    String[] dataArr = msg.split("@@");
                    int respondCode = Integer.parseInt(dataArr[0]);
                    msg = dataArr[1];
                    txt_username.setText("");
                    txt_password.setText("");
                    showMessage(msg,respondCode); //Check for correction of username and password.

                }
            }
        });


    }

    /**
     * Show message on username password TextView.
     * @param message
     * @param respondCode
     */
    public void showMessage(String message,int respondCode){
        txt_msg1.setText(message);
        txt_msg2.setText(message);
        txt_msg1.setVisibility(View.VISIBLE);
        txt_msg2.setVisibility(View.VISIBLE);
        int color = 0;
        if(respondCode == Resources.CHECKING){ //Checking
            color = 0xffe591ac; // Pink
        }else if(respondCode == Resources.REGIS_SUCCESS){ //
            color = 0xff61ce1b; // Green
        }else{
            color = 0xffff0c1a; // Red
        }
        txt_msg1.setTextColor(color);
        txt_msg2.setTextColor(color);

    }

    /**
     * Return AlertDialog
     * @return
     */
    public AlertDialog getAlertDialog(){
        return alertDialog;
    }

    /**
     * Return Activity.
     * @return
     */
    public Activity getActivity(){
        return activity;
    }

    /**
     * Return controller.
     * @return
     */
    public LoginController getController(){
        return controller;
    }

    /**
     * Set username and password called by autologin.
     * @param username
     * @param password
     */
    public void setUsernamePassword(String username,String password){
        txt_username.setText(username);
        txt_password.setText(password);
    }

}
