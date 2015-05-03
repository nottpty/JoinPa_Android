package com.example.taweesoft.joinpa.Register;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.joinpa.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class RegisterDialog implements Observer{
    private Context context;
    private RegisterDialogController controller;
    private EditText txt_username,txt_password;
    private TextView txt_signUp,txt_msg1,txt_msg2;
    private final int ILLEGAL = 1;
    private final int EXIST = 2;
    private final int SUCCESS = 3;
    public RegisterDialog(Context context){
        this.context = context;
        RegisterDialogModel model = new RegisterDialogModel();
        controller = new RegisterDialogController(this,model);
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data == null) return;
        String msg = (String)data;
        String[] dataArr = msg.split("@@");
        int respondCode = Integer.parseInt(dataArr[0]);
        msg = dataArr[1];
        txt_username.setText("");
        txt_password.setText("");
        showMessage(msg,respondCode);
    }

    public void openDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.register_dialog);
        txt_username = (EditText)dialog.findViewById(R.id.txt_username);
        txt_password=  (EditText)dialog.findViewById(R.id.txt_password);
        txt_signUp = (TextView)dialog.findViewById(R.id.txt_signUp);
        txt_msg1 = (TextView)dialog.findViewById(R.id.txt_msg1);
        txt_msg2 = (TextView)dialog.findViewById(R.id.txt_msg2);
    }

    public void setSignUpAction (View.OnClickListener action){
        txt_signUp.setOnClickListener(action);
    }

    public String getUsername(){
        return txt_username.getText().toString();
    }

    public String getPassword(){
        return txt_password.getText().toString();
    }

    public void showMessage(String message,int respondCode){
        txt_msg1.setText(message);
        txt_msg2.setText(message);
        txt_msg1.setVisibility(View.VISIBLE);
        txt_msg2.setVisibility(View.VISIBLE);
        int color = 0;
        if(respondCode == SUCCESS){
            color = 0xff61ce1b;
        }else{
            color = 0xffff0c1a;
        }
        txt_msg1.setTextColor(color);
        txt_msg2.setTextColor(color);

    }
}
