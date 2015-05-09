package com.joinpa.joinpa.joinpa.Library;

import java.io.Serializable;

/**
 * Load data interface.
 * Created on 4/5/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public interface LoadData extends Serializable{
    void load();
    boolean isFinished();
}
