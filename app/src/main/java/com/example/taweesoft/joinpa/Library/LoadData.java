package com.example.taweesoft.joinpa.Library;

import android.os.AsyncTask;

import java.io.Serializable;

/**
 * Load data interface.
 * Created on 4/5/2558.
 */
public interface LoadData extends Serializable{
    void load();
    boolean isFinished();
}
