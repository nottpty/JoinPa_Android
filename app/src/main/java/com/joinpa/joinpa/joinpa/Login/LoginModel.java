package com.joinpa.joinpa.joinpa.Login;

import android.os.AsyncTask;
import android.util.Log;

import com.joinpa.joinpa.joinpa.Library.Database;
import com.joinpa.joinpa.joinpa.Library.Owner;
import com.joinpa.joinpa.joinpa.Library.Resources;
import com.joinpa.joinpa.joinpa.Login.LoginState.LoginState;

import java.util.Observable;


/**
 * Login model.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class LoginModel extends Observable {
    /*Observer is LoginDialog*/

    private Owner owner=null;
    private LoginState state;

    /**
     * Sign in.
     * @param username
     * @param password
     */
    public void doSignIn(final String username,final String password){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                owner = Database.getOwner(username,password);
                Log.d("TTTTTTT : ", owner.getUsername());
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

    /**
     * Sign up.
     * @param username
     * @param password
     */
    public void doSignUp(final String username, final String password){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                setChanged();
                if(Resources.isIllegalText(username, password))
                    notifyObservers("1@@Invalid character");
                else if(Database.checkExistUsername(username))
                    notifyObservers("2@@Username already exist");
                else{
                    Database.addNewUser(username.toLowerCase(),password,Resources.deviceID);
                    notifyObservers("3@@Sign up successful. Please login");
                }
                return null;
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
