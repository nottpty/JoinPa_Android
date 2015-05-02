package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class LoadMyEvent implements Serializable{
    private Owner owner;
    private AsyncTask<Void,Void,Void> loadTask;
    private boolean isFinished = false;
    public LoadMyEvent(Owner owner){
        this.owner = owner;
        Log.d("OWNER LOADMYEVENT : ", owner.hashCode()+"");
        loadTask = new LoadTask();
    }

    public void load(){
        loadTask.execute();
    }

    public boolean isDone(){
        return isFinished;
    }
    class LoadTask extends AsyncTask<Void,Void,Void> implements Serializable{
        @Override
        protected Void doInBackground(Void... params) {
            owner.setEventList(Database.getMyEvent(owner));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("KKK1 : " , isFinished+"");
            isFinished = true;
            Log.d("KKK2 : " , isFinished+"");
        }
    }
}
