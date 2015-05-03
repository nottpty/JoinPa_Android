package com.example.taweesoft.joinpa.Register;

import android.view.View;

/**
 * Created by taweesoft on 3/5/2558.
 */
public class RegisterDialogController {
    private RegisterDialog view;
    private RegisterDialogModel model;

    public RegisterDialogController(RegisterDialog view, RegisterDialogModel model){
        this.view = view;
        this.model = model;
        model.addObserver(view);
    }



    class SignUpAction implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String username = view.getUsername();
            String password = view.getPassword();
            model.doSignUp(username,password);
        }
    }
}
