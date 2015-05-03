package com.example.taweesoft.joinpa.Login.LoginState;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.taweesoft.joinpa.FirstActivity;
import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginDialog;
import com.example.taweesoft.joinpa.MainActivity;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class SuccessState implements LoginState{
    public void performed(Activity firstActivity){
        Intent intent = new Intent(firstActivity,MainActivity.class);
        firstActivity.startActivity(intent);
        firstActivity.finish();
    }
}
