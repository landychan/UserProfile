package com.landychan.userprofile;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    HashMap<String, UserDetails> usersMap;
    Button buttonLogIn;
    Button buttonRegister;
    EditText editUsername;
    EditText editPassword;
    ConstraintLayout layout;
    boolean pressedButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout_main);
        buttonLogIn = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);

        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(!pressedButton) {
                        pressedButton = true;
                        userLogIn();
                        return true;
                    }
                }
                return false;
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogIn();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!pressedButton) {
                    pressedButton = true;
                    Intent registerActivityIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(registerActivityIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        usersMap = Utils.loadUsersMap(this);
        pressedButton = false;
    }

    private void userLogIn() {

        if(pressedButton) {
            return;
        }
        Utils.hideKeyboard(this, layout);

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        boolean hasErrors = false;
        // Reset errors.
        editUsername.setError(null);
        editPassword.setError(null);


        if(TextUtils.isEmpty(username)) {
            editUsername.setError(getString(R.string.empty_username));
            hasErrors = true;
        }

        if(TextUtils.isEmpty(password)) {
            editPassword.setError(getString(R.string.empty_password));
            hasErrors = true;
        }

        if(hasErrors) {
            pressedButton = false;
            return;
        }

        if(usersMap == null) {
            usersMap = Utils.loadUsersMap(this);
        }

        if(usersMap.containsKey(username)) {
            UserDetails user = usersMap.get(username);

            if(user != null) {
                if (Objects.equals(password, user.password)) {
                    Gson gson = new Gson();
                    String serializedUser = gson.toJson(user);
                    Intent userDetailsIntent = new Intent(this, UserInfoActivity.class);
                    userDetailsIntent.putExtra("userdetails", serializedUser);
                    startActivity(userDetailsIntent);
                    return;
                } else {
                    editPassword.setError(getString(R.string.error_incorrect_password));
                }
            }
        }

        // log in fail

        Snackbar snackbar = Snackbar.make(layout, "Failed to log in...", Snackbar.LENGTH_LONG);
        snackbar.show();

        pressedButton = false;
    }
}
