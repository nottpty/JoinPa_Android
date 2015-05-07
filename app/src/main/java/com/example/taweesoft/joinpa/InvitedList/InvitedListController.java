package com.example.taweesoft.joinpa.InvitedList;

import com.example.taweesoft.joinpa.Library.Resources;
import com.example.taweesoft.joinpa.Library.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Invited list controller.
 */
public class InvitedListController {
    private InvitedListDialog view;
    private InvitedListModel model;

    /**
     * Constructor.
     * @param view
     * @param model
     */
    public InvitedListController(InvitedListDialog view, InvitedListModel model){
        this.view = view;
        this.model = model;
    }

    /**
     * Call sortedMap.
     * @param unsortMap
     * @return
     */
    public List<Map<User,Integer>> sortedMap(Map<User,Integer> unsortMap){
        return model.sortedMap(unsortMap);
    }
}
