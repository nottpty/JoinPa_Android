package com.example.taweesoft.joinpa;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginDialog;
import com.google.android.gcm.GCMRegistrar;

import java.io.File;
import java.util.Scanner;


public class FirstActivity extends Activity {
    private TextView btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_first);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        GCMRegistrar.register(this,GCMIntentService.SENDER_ID);
        initComponents();
        AutoLogin();
    }

    /**
     * Initial componenets.
     */
    public void initComponents(){
        btn_signIn = (TextView)findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new ShowSignInDialog());
    }

    class ShowSignInDialog implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            LoginDialog loginDialog = new LoginDialog(FirstActivity.this);
            loginDialog.openDialog();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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

    public void AutoLogin(){
        try{
            if(!Resources.file.exists())
                Resources.file.createNewFile();
            else{
                Scanner scan = new Scanner(Resources.file);
                int status = (Integer.parseInt(scan.next()));
                if(status == Resources.IS_AUTOLOGIN){
                    String username = scan.next();
                    String password = scan.next();
                    LoginDialog loginDialog = new LoginDialog(FirstActivity.this);
                    loginDialog.setUsernamePassword(username,password);
                    loginDialog.getController().login(loginDialog.getAlertDialog());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
