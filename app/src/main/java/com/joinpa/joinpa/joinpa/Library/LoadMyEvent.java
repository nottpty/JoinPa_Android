package com.joinpa.joinpa.joinpa.Library;

import android.os.AsyncTask;

import java.util.Observer;

/**
 * Load my event.
 * Created on 2/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class LoadMyEvent implements LoadData,Observer{
    private Owner owner;
    private boolean isFinished = false;

    /**
     * Constructor.
     * @param owner
     */
    public LoadMyEvent(Owner owner){
        this.owner = owner;
    }

    /**
     * Load my event in background.
     * @param params
     * @return
     */
    protected Void doInBackground(Void... params) {
        owner.setEventList(Database.getMyEvent(owner));
        return null;
    }

    /**
     * Return is load data finished.
     * @return
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Load the data.
     */
    @Override
    public void load() {
        LoadMyEventTask task = new LoadMyEventTask(this);
        task.execute();
    }

    /**
     * Update method for observer. called from LoadMyEventTask.
     * @param observable
     * @param data
     */
    @Override
    public void update(java.util.Observable observable, Object data) {
        isFinished = true;
    }

    /**
     * Load my event task.
     */
    class LoadMyEventTask extends AsyncTask<Void,Void,Void>{
        private Observable observable;
        public LoadMyEventTask(Observer observer){
            observable = new Observable();
            observable.addObserver(observer); //Observer = LoadMyEvent
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
