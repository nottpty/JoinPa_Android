package com.joinpa.joinpa.joinpa.FindFriend;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.State.AddState;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.State.FoundState;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.State.NotFoundState;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.Strategy.DatabaseFinder;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.Strategy.Finder;
import com.joinpa.joinpa.joinpa.FindFriend.FindFriend.Strategy.LocalFinder;
import com.joinpa.joinpa.joinpa.Library.Friend;
import com.joinpa.joinpa.joinpa.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Finf friend dialog.
 * Created on 30/4/2558.
 * Created by TAWEERAT CHAIMAN 5710546259, PATINYA YONGYAI 5710547204
 */
public class FindFriendDialog implements Observer{
    private Context context;
    private Finder localFinder = new LocalFinder(this);
    private Finder databaseFinder = new DatabaseFinder(this);
    private EditText txt_username;
    private TextView txt_tapToAdd;
    private Button btn_find;
    private Finder finder;
    private Friend friend;
    private FindFriendController controller;

    /*States*/
    public AddState foundState = new FoundState();
    public AddState notFoundState = new NotFoundState();

    /**
     * Constructor.
     * @param context
     */
    public FindFriendDialog(Context context){
        this.context = context;
        finder = localFinder;
        FindFriendModel model = new FindFriendModel();
        controller = new FindFriendController(this,model);
        controller.setState(notFoundState);
    }

    /**
     * Open find friend dialog.
     */
    public void openDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Find friend");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Hide the action bar.
        dialog.setContentView(R.layout.activity_find_friend);
        txt_username = (EditText)dialog.findViewById(R.id.txt_username);
        btn_find = (Button)dialog.findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new FindEvent());
        txt_username.setOnClickListener(new AddFriendAction());

        txt_tapToAdd = (TextView)dialog.findViewById(R.id.txt_tapToAdd);

        dialog.show();
    }

    /**
     * Update method of observer.
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        if(((Object)observable).getClass() == DatabaseFinder.class){
            if(data == null) {
                txt_username.setBackgroundColor(0xffff4d32);
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
            txt_username.setBackgroundColor(0xff88ff47);
            txt_tapToAdd.setText("Tap to add   ");
            controller.setState(foundState);
        }else{
            txt_username.setBackgroundColor(0xfffdff34);
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

    /**
     * Get controller.
     * @return
     */
    public FindFriendController getController(){
        return controller;
    }

    /**
     * Add friend action.
     */
    class AddFriendAction implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            controller.addNewFriend(FindFriendDialog.this,friend);
        }
    }


    /**
     * Get context.
     * @return
     */
    public Context getContext(){
        return context;
    }
}
