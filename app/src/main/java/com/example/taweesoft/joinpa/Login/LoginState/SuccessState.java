package com.example.taweesoft.joinpa.Login.LoginState;

import android.content.Intent;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Login.LoginActivity;
import com.example.taweesoft.joinpa.MainActivity;

/**
 * Created by taweesoft on 27/4/2558.
 */
public class SuccessState implements LoginState{
    public void performed(LoginActivity loginAct){
        Intent intent = new Intent(loginAct, MainActivity.class);
        Resources.owner = loginAct.getOwner();
        loginAct.startActivity(intent);
        loginAct.finish();
    }
}
