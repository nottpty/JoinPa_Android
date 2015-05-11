package com.joinpa.joinpa.joinpa.Library;

import android.os.AsyncTask;

import java.util.*;

/**
 * Load friend list.
 * Created on 11/5/2558.
 */
public class LoadFriendList implements LoadData,Observer{
    private Owner owner;
    private boolean isFinished = false;

    /**
     * Constructor.
     * @param owner
     */
    public LoadFriendList(Owner owner){
        this.owner = owner;
    }

    /**
     * Load friend list.
     */
    @Override
    public void load() {
        LoadFriendListTask task = new LoadFriendListTask(this);
        task.execute();
    }

    /**
     * Return status on loading.
     * @return
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Update method for observer. Called from
     * @param observable
     * @param data
     */
    @Override
    public void update(java.util.Observable observable, Object data) {
        isFinished = true;
    }

    class LoadFriendListTask extends AsyncTask<Void,Void,Void>{
        private Observable observable;

        public LoadFriendListTask(Observer observer){
            observable = new Observable();
            observable.addObserver(observer);
        }
        @Override
        protected Void doInBackground(Void... params) {
            owner.setFriendList(Database.getFriendList(owner));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            observable.setChanged();
            observable.notifyObservers();
        }
    }


}
