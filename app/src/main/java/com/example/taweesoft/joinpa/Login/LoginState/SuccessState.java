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
 * Created by taweesoft on 27/4/2558.
 */
public class SuccessState implements LoginState{
    public void performed(Activity firstActivity){
        try{
            FileOutputStream stream = new FileOutputStream(Resources.file);
            stream.write(("1 " + Resources.owner.getUsername() + " " + Resources.password).getBytes());
            Log.d("XXXX : ", "1 " + (Resources.owner.getUsername() + " " + Resources.password));
            stream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(firstActivity,MainActivity.class);
        firstActivity.startActivity(intent);
        firstActivity.finish();
    }
}
