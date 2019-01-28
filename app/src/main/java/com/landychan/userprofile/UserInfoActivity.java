package com.landychan.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class UserInfoActivity extends AppCompatActivity {

    UserDetails currentUser;
    TextView tvUsername;
//    TextView tvPassword;
    TextView tvEmail;
    TextView tvFirstName;
    TextView tvLastName;
    TextView tvAddressOne;
    TextView tvAddressTwo;
    TextView tvCity;
    TextView tvState;
    TextView tvZip;
    TextView tvBirthdate;
    Button buttonEdit;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        gson = new Gson();

        tvUsername = findViewById(R.id.tv_username);
//        tvPassword = findViewById(R.id.tv_password);
        tvEmail = findViewById(R.id.tv_email_address);
        tvFirstName = findViewById(R.id.tv_first_name);
        tvLastName = findViewById(R.id.tv_last_name);
        tvAddressOne = findViewById(R.id.tv_address);
        tvAddressTwo = findViewById(R.id.tv_address_line_two);
        tvCity = findViewById(R.id.tv_city);
        tvState = findViewById(R.id.tv_state);
        tvZip = findViewById(R.id.tv_zip_code);
        tvBirthdate = findViewById(R.id.tv_birthdate);
        buttonEdit = findViewById(R.id.button_edit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serializedUser = gson.toJson(currentUser);
                Intent editUserIntent = new Intent(UserInfoActivity.this, RegisterActivity.class);
                editUserIntent.putExtra("edituser", true);
                editUserIntent.putExtra("userdetails", serializedUser);
                startActivityForResult(editUserIntent, Utils.UPDATE_USER);
            }
        });
        if(getUser()) {
            showUserDetails();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Utils.UPDATE_USER) {
            if(resultCode == RESULT_OK) {
                String serializedUser = intent.getStringExtra("userdetails");

                if (serializedUser != null) {
                    currentUser = gson.fromJson(serializedUser, UserDetails.class);
                    showUserDetails();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

    private void showUserDetails() {
        if(currentUser != null) {
            tvUsername.setText(currentUser.username);
            tvEmail.setText(currentUser.email);
            tvFirstName.setText(currentUser.firstName);
            tvLastName.setText(currentUser.lastName);
            tvAddressOne.setText(currentUser.addressOne);
            tvAddressTwo.setText(currentUser.addressTwo);
            tvCity.setText(currentUser.city);
            tvState.setText(currentUser.state);
            tvZip.setText(currentUser.zip);
            tvBirthdate.setText(currentUser.birthdate);
        }
    }


    private boolean getUser() {
        String serializedUser = getIntent().getStringExtra("userdetails");

        if(serializedUser != null) {
            currentUser = gson.fromJson(serializedUser, UserDetails.class);
            return true;
        }

        return false;
    }

}
