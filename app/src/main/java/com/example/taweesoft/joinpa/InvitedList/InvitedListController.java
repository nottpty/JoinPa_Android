package com.example.taweesoft.joinpa.InvitedList;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class InvitedListController {
    private InvitedListDialog view;
    private InvitedListModel model;

    public InvitedListController(InvitedListDialog view, InvitedListModel model){
        this.view = view;
        this.model = model;
    }

    public List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
        return model.sortedMap(unsortMap);
    }
}
