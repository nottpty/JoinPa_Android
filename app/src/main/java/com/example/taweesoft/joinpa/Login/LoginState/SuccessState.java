package com.example.taweesoft.joinpa.Login.LoginState;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taweesoft.joinpa.FirstActivity;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginDialog;
import com.example.taweesoft.joinpa.MainActivity;

import java.io.FileOutputStream;

/**
 * Login success state.
 */
public class SuccessState implements LoginState{

    /**
     * Write file into internal directory to use auto login in next time.
     * @param firstActivity
     */
    public void performed(Activity firstActivity){
        try{
            FileOutputStream stream = new FileOutputStream(Resources.file);
            stream.write(("1 " + Resources.owner.getUsername() + " " + Resources.password).getBytes());
            stream.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        /*Start main activity.*/
        Intent intent = new Intent(firstActivity,MainActivity.class);
        firstActivity.startActivity(intent);
        firstActivity.finish();
    }
}
