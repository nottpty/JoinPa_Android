package com.example.taweesoft.joinpa.Login;

import android.os.AsyncTask;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Owner;
import com.example.taweesoft.joinpa.Login.LoginState.LoginState;

import java.util.Observable;


/**
 * Created by taweesoft on 30/4/2558.
 */
public class LoginModel extends Observable {
    private Owner owner=null;
    private LoginState state;

    public void doLogin(final String username,final String password){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                owner = Database.getOwner(username,password);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setChanged();
                notifyObservers(owner);
            }

        };
        task.execute();
    }

    public LoginState getState(){
        return state;
    }

    public void setState(LoginState state){
        this.state = state;
    }

}
