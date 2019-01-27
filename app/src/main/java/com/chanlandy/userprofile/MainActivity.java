package com.chanlandy.userprofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_login) Button buttonLogIn;
    @BindView(R.id.button_register) Button buttonRegister;
    @BindView(R.id.edit_username) EditText editUsername;
    @BindView(R.id.edit_password) EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void userLogIn() {

    }
}
