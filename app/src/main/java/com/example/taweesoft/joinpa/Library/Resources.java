package com.example.taweesoft.joinpa.Library;

import android.os.Environment;

import com.example.taweesoft.joinpa.R;

import java.io.File;
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
    public static final int CHECKING = 0;
    public static final int ILLEGAL = 1;
    public static final int EXIST = 2;
    public static final int REGIS_SUCCESS = 3;
    public static final int LOGIN_SUCCESS = 4;
    public static final int IS_AUTOLOGIN = 1;
    public static final int NOT_AUTOLOGIN = 2;
    public static List<Integer> icons = new ArrayList<Integer>(Arrays.asList(
            R.drawable.toilet_small,
            R.drawable.lunch_small,
            R.drawable.playgame_small,
            R.drawable.home_small,
            R.drawable.seven_eleven_small
    ));

    public static List<Integer> iconsBig = new ArrayList<Integer>(Arrays.asList(
            R.drawable.toilet_big,
            R.drawable.lunch_big,
            R.drawable.playgame_big,
            R.drawable.home_big,
            R.drawable.seven_eleven_big
    ));

    public static Map<Integer,String> eventsName= new HashMap<Integer,String>();

    public static final String URL = "http://www.cmvk-tech.com/joinpa/connectDB.php";

    static{
        eventsName.put(icons.get(0),"Toilet");
        eventsName.put(icons.get(1),"Lunch");
        eventsName.put(icons.get(2),"Play game");
        eventsName.put(icons.get(3),"Home");
        eventsName.put(icons.get(4),"7-Eleven");
    }

    public static String deviceID = "key";

    public static Owner owner = null;

    public static boolean isIllegalText(String username,String password){
        final String illegalText = "#$%~!@^&*()-_=+,.;\'\"<>?";
        return username.matches(illegalText) || password.matches(illegalText);
    }

    public static File sdCard = Environment.getExternalStorageDirectory();
    public static File dir = new File (sdCard.getAbsolutePath() + "/");
    public static File file = new File(dir,"JoinPa.txt");

    public static String password = "";
}
