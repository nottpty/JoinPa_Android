package com.example.taweesoft.joinpa.Register;

import android.os.AsyncTask;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Resources;

import java.util.Observable;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class RegisterDialogModel extends Observable{

    public void doSignUp(final String username, final String password){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                setChanged();
                if(Resources.isIllegalText(username,password))
                    notifyObservers("1@@Username or password has illegal character");
                else if(Database.checkExistUsername(username))
                    notifyObservers("2@@Username already exist");
                else{
                    Database.addNewUser(username,password,Resources.deviceID);
                    notifyObservers("3@@Sign up successful\nPlease login");
                }
                return null;
            }
        };
        task.execute();

    }


}
