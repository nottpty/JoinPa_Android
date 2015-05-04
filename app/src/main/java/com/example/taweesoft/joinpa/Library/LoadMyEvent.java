package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.util.Observer;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class LoadMyEvent implements LoadData,Observer{
    private Owner owner;
    private boolean isFinished = false;

    public LoadMyEvent(Owner owner){
        this.owner = owner;
        Log.d("OWNER LOADMYEVENT : ", owner.hashCode() + "");
    }
    protected Void doInBackground(Void... params) {
        owner.setEventList(Database.getMyEvent(owner));
        return null;
    }

    public boolean isFinished() {
        return isFinished;
    }


    @Override
    public void load() {
        LoadMyEventTask task = new LoadMyEventTask(this);
        task.execute();
    }

    @Override
    public void update(java.util.Observable observable, Object data) {
        isFinished = true;
    }

    class LoadMyEventTask extends AsyncTask<Void,Void,Void>{
        private Observable observable;
        public LoadMyEventTask(Observer observer){
            observable = new Observable();
            observable.addObserver(observer);
        }
        @Override
        protected Void doInBackground(Void... params) {
            owner.setEventList(Database.getMyEvent(owner));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            observable.setChanged();
            observable.notifyObservers();
        }
    }
}
