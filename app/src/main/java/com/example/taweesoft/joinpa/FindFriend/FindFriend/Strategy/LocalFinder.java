package com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy;

import com.example.taweesoft.joinpa.Library.Database;
import com.example.taweesoft.joinpa.Library.Friend;

import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class LocalFinder implements Finder {

    @Override
    public Friend find(String username) {
        Map<String,Friend> allUser = Database.allUser;
        return allUser.get(username);
    }
}
