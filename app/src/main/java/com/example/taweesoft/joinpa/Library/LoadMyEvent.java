package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;

/**
 * Created by taweesoft on 2/5/2558.
 */
public class LoadMyEvent {
    private Owner owner;
    private AsyncTask<Void,Void,Void> loadTask;
    public LoadMyEvent(Owner owner){
        this.owner = owner;
        loadTask = new LoadTask();
    }

    public void load(){
        loadTask.execute();
    }

    public boolean isDone(){
        return loadTask.getStatus() == AsyncTask.Status.FINISHED;
    }
    class LoadTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            owner.setEventList(Database.getMyEvent(owner));
            return null;
        }
    }
}
