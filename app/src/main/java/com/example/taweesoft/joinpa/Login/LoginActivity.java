package com.example.taweesoft.joinpa.Login;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.Login.LoginState.FailedState;
import com.example.taweesoft.joinpa.Login.LoginState.LoginState;
import com.example.taweesoft.joinpa.Login.LoginState.SuccessState;
import com.example.taweesoft.joinpa.GCMIntentService;
import com.example.taweesoft.joinpa.R;
import com.google.android.gcm.GCMRegistrar;

import java.util.Observable;
import java.util.Observer;


public class LoginActivity extends ActionBarActivity implements Observer {
    private EditText txt_username,txt_password;
    private Button btn_login;
    private Owner owner = null;
    private final LoginState successState = new SuccessState();
    private final LoginState failedState = new FailedState();
    private LoginController controller;
    private AlertDialog.Builder loginDialogBuilder;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        GCMRegistrar.register(LoginActivity.this,GCMIntentService.SENDER_ID);

        initComponents();
        loginDialogBuilder = new AlertDialog.Builder(this);
        dialog = loginDialogBuilder.create();

        LoginModel model = new LoginModel();
        controller = new LoginController(this,model);
    }

    /**
     * Initial components
     */
    public void initComponents(){
        txt_username = (EditText)findViewById(R.id.txt_username);
        txt_password = (EditText)findViewById(R.id.txt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
    }

    /**
     * Set action for login button.
     */
    public void setLoginAction( View.OnClickListener action){
        btn_login.setOnClickListener(action);
    }




    /**
     * Get Owner.
     * @return owner.
     */
    public Owner getOwner() {
        return owner;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getUsername(){
        return txt_username.getText().toString();
    }

    public String getPassword(){
        return txt_password.getText().toString();
    }


    @Override
    public void update(Observable observable, Object data) {
        Owner owner = (Owner)data;
        if(owner == null){
            dialog.setMessage("Wrong username or password");
            controller.setState(failedState);
        }
        else{
            dialog.setMessage("Login successful");
            controller.setState(successState);
            this.owner = owner;
        }
        dialog.setCancelable(true);
    }

    public AlertDialog getAlertDialog(){
        return dialog;
    }

}
