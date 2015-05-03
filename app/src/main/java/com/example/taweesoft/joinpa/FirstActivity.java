package com.example.taweesoft.joinpa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.taweesoft.joinpa.Login.LoginDialog;
import com.google.android.gcm.GCMRegistrar;


public class FirstActivity extends ActionBarActivity {
    private Button btn_signIn,btn_signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        GCMRegistrar.register(this,GCMIntentService.SENDER_ID);
        initComponents();
    }

    /**
     * Initial componenets.
     */
    public void initComponents(){
        btn_signIn = (Button)findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new ShowSignInDialog());
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
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
}
