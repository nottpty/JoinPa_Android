package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Observer;

/**
 * Created by taweesoft on 4/5/2558.
 */
public class LoadMyJoinedEvent implements Observer,LoadData{
    private Owner owner;
    private boolean isFinished = false;
    public LoadMyJoinedEvent(Owner owner){
        this.owner = owner;
    }

    public boolean isFinished() {
        return isFinished;
    }


    @Override
    public void update(java.util.Observable observable, Object data) {
        isFinished = true;
    }

    @Override
    public void load() {
        LoadJoinedEventTask task = new LoadJoinedEventTask(this);
        task.execute();
    }

    class LoadJoinedEventTask extends AsyncTask<Void,Void,Void>{
        private Observable observable;
        public LoadJoinedEventTask(Observer observer){
            observable = new Observable();
            observable.addObserver(observer);
        }
        @Override
        protected Void doInBackground(Void... params) {
            owner.setJoinedEvent(Database.myJoiningEvents(owner, Resources.JOIN));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("DDDD : ", owner.getJoinedEvent().size()+"");
            observable.setChanged();
            observable.notifyObservers();
        }
    }
}
