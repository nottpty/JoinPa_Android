package com.example.taweesoft.joinpa.Library;

import com.example.taweesoft.joinpa.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 29/4/2558.
 */
public class Resources {
    public static final int WAITING = 0;
    public static final int JOIN = 1;
    public static final int DECLINE = 2;
    public static List<Integer> icons = new ArrayList<Integer>(Arrays.asList(
            R.drawable.a0,
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3
    ));

    public static Map<Integer,String> eventsName= new HashMap<Integer,String>();

    public static final String URL = "http://www.cmvk-tech.com/joinpa/connectDB.php";

    static{
        eventsName.put(icons.get(0),"Toilet");
        eventsName.put(icons.get(1),"Lunch");
        eventsName.put(icons.get(2),"Play game");
        eventsName.put(icons.get(3),"Home");

    }

    public static String deviceID = "key";

    public static Owner owner = null;
}
