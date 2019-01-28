package com.landychan.userprofile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvPassword;
    TextView tvEmail;
    TextView tvFirstName;
    TextView tvLastName;
    TextView tvAddressOne;
    TextView tvAddressTwo;
    TextView tvCity;
    TextView tvState;
    TextView tvZip;
    Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tvUsername = findViewById(R.id.tv_username);
        tvPassword = findViewById(R.id.tv_password);
        tvEmail = findViewById(R.id.tv_email_address);
        tvFirstName = findViewById(R.id.tv_first_name);
        tvLastName = findViewById(R.id.tv_last_name);
        tvAddressOne = findViewById(R.id.tv_address);
        tvAddressTwo = findViewById(R.id.tv_address_line_two);
        tvCity = findViewById(R.id.tv_city);
        tvState = findViewById(R.id.tv_state);
        tvZip = findViewById(R.id.tv_zip_code);
        buttonEdit = findViewById(R.id.button_edit);

    }

}
