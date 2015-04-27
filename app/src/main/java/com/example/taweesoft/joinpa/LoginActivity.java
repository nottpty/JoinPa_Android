package com.example.taweesoft.joinpa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taweesoft.joinpa.LoginState.FailedState;
import com.example.taweesoft.joinpa.LoginState.LoginState;
import com.example.taweesoft.joinpa.LoginState.SuccessState;


public class LoginActivity extends ActionBarActivity {
    private EditText txt_username,txt_password;
    private Button btn_login;
    private static Owner owner = null;
    private final LoginState successState = new SuccessState();
    private final LoginState failedState = new FailedState();
    private LoginState state;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        LoginEvent loginEvent = new LoginEvent(txt_username,txt_password);
        btn_login.setOnClickListener(loginEvent);
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
     * Login event
     */
    class LoginEvent implements View.OnClickListener {
        private EditText txt_username,txt_password;
        public LoginEvent(EditText username,EditText password){
            this.txt_username = username;
            this.txt_password = password;
        }
        public void onClick(View v){
            AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                AlertDialog.Builder loginDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                AlertDialog loginDialog;
                protected void onPreExecute(){
                    loginDialogBuilder.setMessage("Logging in...");
                    loginDialogBuilder.setCancelable(false);
                    loginDialog = loginDialogBuilder.create();
                    loginDialog.setOnCancelListener(new DismissLoginDialogEvent());
                    loginDialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    String username = txt_username.getText().toString();
                    String password = txt_password.getText().toString();
                    owner = Database.getOwner(username,password);
                    return null;
                }

                protected void onPostExecute(Void param){
                    if(owner == null){
                        loginDialog.setMessage("Wrong username or password");
                        state = failedState;
                    }
                    else{
                        loginDialog.setMessage("Login successful");
                        state = successState;
                    }
                    loginDialog.setCancelable(true);
                }
            };
            task.execute();
        }
    }

    class DismissLoginDialogEvent implements DialogInterface.OnCancelListener{

        public void onCancel(DialogInterface dialog){
            state.performed(LoginActivity.this);
        }
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
}
