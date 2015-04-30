package com.example.taweesoft.joinpa.FindFriend;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.DatabaseFinder;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.Finder;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.LocalFinder;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class FindFriendDialog {
    private MainActivity mainActivity;
    private static Finder localFinder = new LocalFinder();
    private static Finder databaseFinder = new DatabaseFinder();
    private EditText txt_username;
    private Button btn_find;
    private TextView txt_showFriendName;
    private Finder finder;
    public FindFriendDialog(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        finder = localFinder;
    }

    public void openDialog(){
        Dialog dialog = new Dialog(mainActivity);
        dialog.setTitle("Find friend");
        dialog.setContentView(R.layout.activity_find_friend);
        txt_username = (EditText)dialog.findViewById(R.id.txt_username);
        btn_find = (Button)dialog.findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new FindEvent());
        txt_showFriendName = (TextView)dialog.findViewById(R.id.txt_showFriendName);
        dialog.show();
    }

    /**
     * Set find button event.
     */


    /**
     * Find friend.
     */
    class FindEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String username = txt_username.getText().toString();
            Friend friend = finder.find(username);
            if(friend == null)
                finder = databaseFinder;
            friend = finder.find(username);
            if(friend != null){
                txt_showFriendName.setText(friend.getUsername());
            }
        }
    }
}
