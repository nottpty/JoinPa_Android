package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Observer;

/**
 * Load my joined event.
 * Created on 4/5/2558.
 */
public class LoadMyJoinedEvent implements Observer,LoadData{
    private Owner owner;
    private boolean isFinished = false;

    /**
     * Constructor.
     * @param owner
     */
    public LoadMyJoinedEvent(Owner owner){
        this.owner = owner;
    }

    /**
     * Return status of loading data.
     * @return
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Update method of observer. Call from LoadJoinedEventTask.
     * @param observable
     * @param data
     */
    @Override
    public void update(java.util.Observable observable, Object data) {
        isFinished = true;
    }

    /**
     * Load the event.
     */
    @Override
    public void load() {
        LoadJoinedEventTask task = new LoadJoinedEventTask(this);
        task.execute();
    }

    /**
     * Load task.
     */
    class LoadJoinedEventTask extends AsyncTask<Void,Void,Void>{
        private Observable observable;
        public LoadJoinedEventTask(Observer observer){
            observable = new Observable();
            observable.addObserver(observer); // Observer = LoadMyJoinedEvent
        }
        @Override
        protected Void doInBackground(Void... params) {
            owner.setJoinedEvent(Database.myJoiningEvents(owner, Resources.JOIN));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            observable.setChanged();
            observable.notifyObservers();
        }
    }
}
