package com.landychan.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    HashMap<String, UserDetails> usersMap;
    UserDetails user;
    boolean isEditMode = false;
    Gson gson;
    EditText editUsername;
    EditText editPassword;
    EditText editEmail;
    EditText editFirstName;
    EditText editLastName;
    EditText editAddressOne;
    EditText editAddressTwo;
    EditText editCity;
    EditText editState;
    EditText editZip;
    Button buttonRegister;
// Add birth date

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        gson = new Gson();

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("edituser", false);

        if(isEditMode) {
            String serializedUser = intent.getStringExtra("userdetails");
            user = gson.fromJson(serializedUser, UserDetails.class);
        }

        usersMap = Utils.loadUsersMap(this);

        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        editEmail = findViewById(R.id.edit_email_address);
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editAddressOne = findViewById(R.id.edit_address);
        editAddressTwo = findViewById(R.id.edit_address_line_two);
        editCity = findViewById(R.id.edit_city);
        editState = findViewById(R.id.edit_state);
        editZip = findViewById(R.id.edit_zip_code);
        buttonRegister = findViewById(R.id.button_register);

        if(isEditMode) {
            buttonRegister.setText("Save");
            showUserDetails();
        }

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String email = editEmail.getText().toString();
                String firstname = editFirstName.getText().toString();
                String lastname = editLastName.getText().toString();
                String addressone = editAddressOne.getText().toString();
                String addresstwo = editAddressTwo.getText().toString();
                String city = editCity.getText().toString();
                String state = editState.getText().toString();
                String zip = editZip.getText().toString();

                UserDetails newUser = new UserDetails(username, password, email, firstname, lastname, addressone, addresstwo, city, state, zip);

                if(isEditMode) {
                    saveUserEdits(newUser);
                } else {
                    registerUser(newUser);
                }
            }
        });
    }



    private void registerUser(UserDetails user) {

        boolean duplicateUserExists = checkDuplicateUser(user.username);

        if(!duplicateUserExists) {
            //check all fields
            usersMap.put(user.username, user);
            Utils.saveUsers(this, usersMap);
            finish();
        }
    }


    private boolean checkDuplicateUser(String username) {
        return usersMap.containsKey(username);
    }

    private void showUserDetails() {
        if(user != null) {
            editUsername.setText(user.username);
            editPassword.setText(user.password);
            editEmail.setText(user.email);
            editFirstName.setText(user.firstName);
            editLastName.setText(user.lastName);
            editAddressOne.setText(user.addressOne);
            editAddressTwo.setText(user.addressTwo);
            editCity.setText(user.city);
            editState.setText(user.state);
            editZip.setText(user.zip);
        }
    }

    private void saveUserEdits(UserDetails user) {

        usersMap.put(user.username, user);
        Utils.saveUsers(this, usersMap);

        Intent returnIntent = new Intent();
        String serializedUser = gson.toJson(user);
        returnIntent.putExtra("userdetails", serializedUser);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


}
