package com.landychan.userprofile;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    HashMap<String, UserDetails> usersMap;
    Button buttonLogIn;
    Button buttonRegister;
    EditText editUsername;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogIn = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                userLogIn(username, password);

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivityIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerActivityIntent);
            }
        });
    }

    private void userLogIn(String username, String password) {

        // Reset errors.
        editUsername.setError(null);
        editPassword.setError(null);


        if(TextUtils.isEmpty(username)) {
            editUsername.setError(getString(R.string.empty_username));
        }

        if(TextUtils.isEmpty(password)) {
            editPassword.setError(getString(R.string.empty_password));
        }


        if(usersMap == null) {
            usersMap = Utils.loadUsersMap(this);
        }

        if(usersMap.containsKey(username)) {
            UserDetails user = usersMap.get(username);

            if(user != null) {
                if (Objects.equals(password, user.password)) {

                }
            } else {

                // log in fail
            }
        }

    }
}
