package com.joinpa.joinpa.joinpa.Library;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseArray;

import com.joinpa.joinpa.joinpa.BuildConfig;
import com.joinpa.joinpa.joinpa.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resources file.
 * Created on 29/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class Resources {
    public static final String MY_VERSION = BuildConfig.VERSION_NAME;
    public static String CURRENT_VERSION = "0";
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
            R.drawable.sleep,
            R.drawable.book,
            R.drawable.cinema,
            R.drawable.facebook,
            R.drawable.graduation,
            R.drawable.music,
            R.drawable.football,
            R.drawable.basketball,
            R.drawable.beach,
            R.drawable.bicycle,
            R.drawable.codeing,
            R.drawable.twitter,
            R.drawable.tea,
            R.drawable.coffee,
            R.drawable.ruler,
            R.drawable.karaoke,
            R.drawable.physic,
            R.drawable.chemistry,
            R.drawable.bio
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
            R.drawable.lunch_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.drink_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.toilet_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.playgame_card_bg,
            R.drawable.toilet_card_bg,
            R.drawable.toilet_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.home_card_bg,
            R.drawable.drink_card_bg,
            R.drawable.lunch_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.dota_card_bg,
            R.drawable.fitness_card_bg,
            R.drawable.drink_card_bg

    ));
    public static Map<String,Integer> eventsName= new HashMap<String,Integer>();

    public static List<String> eventNameKey = new ArrayList<String>();

    public static final String URL = "http://www.cmvk-tech.com/joinpa/connectDB.php";

    static{
       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);

       LoadEventName loadEventName = new LoadEventName();
       loadEventName.execute();

       CURRENT_VERSION = getCurrentVersion();
    }

    public static String deviceID = "key";

    public static Owner owner = null;

    public static boolean isIllegalText(String username,String password){
        if(username.length() == 0 || password.length() == 0) return true;
        for(int i=0;i<username.length();i++){
            char chr = username.charAt(i);
            if(!characterRange(chr)) return true;
        }
        for(int i=0;i<password.length();i++){
            char chr = password.charAt(i);
            if(!characterRange(chr)) return true;
        }
        return false;
    }

    public static boolean characterRange(char chr){
        return Character.isLetterOrDigit(chr) && ((chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z') || (chr >= '0' && chr <= '9'));
    }
    public static File sdCard = Environment.getExternalStorageDirectory();
    public static File dir = new File (sdCard.getAbsolutePath() + "/");
    public static File file = new File(dir,"JoinPa.txt");

    public static String password = "";

    public static boolean isNewData = false;

    static class LoadEventName extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            eventsName = Database.loadEventsName();
            eventNameKey.addAll(eventsName.keySet());
            return null;
        }
    }

    public static String getCurrentVersion(){
        return Database.getVersion();
    }

    public static boolean isUpToDate(){
        return MY_VERSION.equals(CURRENT_VERSION);
    }
}
