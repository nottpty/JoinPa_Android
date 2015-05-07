package com.joinpa.taweesoft.joinpa.Library;

import java.io.Serializable;

/**
 * Load data interface.
 * Created on 4/5/2558.
 */
public interface LoadData extends Serializable{
    void load();
    boolean isFinished();
}
