package com.example.taweesoft.joinpa.FindFriend;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taweesoft.joinpa.CreateEvent.NewEvent.NewEventController;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.AddState;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.FoundState;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.State.NotFoundState;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.DatabaseFinder;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.Finder;
import com.example.taweesoft.joinpa.FindFriend.FindFriend.Strategy.LocalFinder;
import com.example.taweesoft.joinpa.Library.Friend;
import com.example.taweesoft.joinpa.MainActivity;
import com.example.taweesoft.joinpa.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 30/4/2558.
 */
public class FindFriendDialog implements Observer{
    private Context context;
    private Finder localFinder = new LocalFinder(this);
    private Finder databaseFinder = new DatabaseFinder(this);
    private EditText txt_username;
    private TextView txt_tapToAdd;
    private Button btn_find;
    private RelativeLayout layout_friendName;
    private Finder finder;
    private Friend friend;
    private FindFriendController controller;

    public AddState foundState = new FoundState();
    public AddState notFoundState = new NotFoundState();

    public FindFriendDialog(Context context){
        this.context = context;
        finder = localFinder;
        FindFriendModel model = new FindFriendModel();
        controller = new FindFriendController(this,model);
        controller.setState(notFoundState);
    }

    public void openDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Find friend");
        dialog.setContentView(R.layout.activity_find_friend);
        txt_username = (EditText)dialog.findViewById(R.id.txt_username);
        btn_find = (Button)dialog.findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new FindEvent());
        layout_friendName = (RelativeLayout)dialog.findViewById(R.id.layout_friendName);
        layout_friendName.setOnClickListener(new AddFriendAction());

        txt_tapToAdd = (TextView)dialog.findViewById(R.id.txt_tapToAdd);

        dialog.show();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(((Object)observable).getClass() == DatabaseFinder.class){
            if(data == null) {
                layout_friendName.setBackgroundColor(0xffff4d32);
                controller.setState(notFoundState);
                txt_tapToAdd.setVisibility(View.INVISIBLE);
                return;
            }
        }
        else if(((Object)observable).getClass() == LocalFinder.class){
            if(data == null){
                finder = databaseFinder;
                finder.find(txt_username.getText().toString());
                return;
            }
        }
        friend = (Friend)data;
        if(!controller.isAlreadyAdd(friend)){
            layout_friendName.setBackgroundColor(0xff88ff47);
            txt_tapToAdd.setText("Tap to add   ");
            controller.setState(foundState);
        }else{
            layout_friendName.setBackgroundColor(0xfffdff34);
            txt_tapToAdd.setText("Already friend with you   ");
        }
        txt_tapToAdd.setVisibility(View.VISIBLE);
    }

    /**
     * Find friend. (Should user observer pattern)
     */
    class FindEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finder = localFinder;
            String username = txt_username.getText().toString();
            finder.find(username);
        }
    }

    public FindFriendController getController(){
        return controller;
    }

    class AddFriendAction implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            controller.addNewFriend(FindFriendDialog.this,friend);
        }
    }



    public Context getContext(){
        return context;
    }
}
