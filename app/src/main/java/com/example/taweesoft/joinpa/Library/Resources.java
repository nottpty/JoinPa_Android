package com.example.taweesoft.joinpa.Library;

import android.os.Environment;
import android.util.Log;

import com.example.taweesoft.joinpa.GCMIntentService;
import com.example.taweesoft.joinpa.R;
import com.google.android.gcm.GCMRegistrar;

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
            R.drawable.toilet,
            R.drawable.lunch,
            R.drawable.playgame,
            R.drawable.home,
            R.drawable.dota,
            R.drawable.drink,
            R.drawable.fitness,
            R.drawable.meeting,
            R.drawable.shopping,
            R.drawable.homework,
            R.drawable.sleep
    ));
    public static List<Integer> cardBG = new ArrayList<Integer>(Arrays.asList(
            R.drawable.toilet_card_bg,
            R.drawable.lunch_card_bg,
            R.drawable.playgame_card_bg,
            R.drawable.home_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.drink_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.drink_card_bg,
            R.drawable.playgame_card_bg,
            R.drawable.lunch_card_bg
    ));
    public static Map<Integer,String> eventsName= new HashMap<Integer,String>();

    public static final String URL = "http://www.cmvk-tech.com/joinpa/connectDB.php";

    static{
        eventsName.put(icons.get(0),"Toilet");
        eventsName.put(icons.get(1),"Lunch");
        eventsName.put(icons.get(2),"Play game");
        eventsName.put(icons.get(3),"Home");
        eventsName.put(icons.get(4),"Dota");
        eventsName.put(icons.get(5),"Drink");
        eventsName.put(icons.get(6),"Fitness");
        eventsName.put(icons.get(7),"Meeting");
        eventsName.put(icons.get(8),"Shopping");
        eventsName.put(icons.get(9),"Homework");
        eventsName.put(icons.get(10),"Sleep");

    }

    public static String deviceID = "key";

    public static Owner owner = null;

    public static boolean isIllegalText(String username,String password){
        final String illegalText = "#$%~!@^&*()-_=+,. ;\'\"<>?";
        if(username.length() == 0 || password.length() == 0) return true;
        for(int i=0;i<username.length();i++){
            String chr = Character.toString(username.charAt(i));
            if(illegalText.contains(chr)) return true;
        }
        for(int i=0;i<password.length();i++){
            String chr = Character.toString(password.charAt(i));
            if(illegalText.contains(chr)) return true;
        }
        return false;
    }

    public static File sdCard = Environment.getExternalStorageDirectory();
    public static File dir = new File (sdCard.getAbsolutePath() + "/");
    public static File file = new File(dir,"JoinPa.txt");

    public static String password = "";

    public static boolean isNewData = false;
}
