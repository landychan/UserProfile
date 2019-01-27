package com.landychan.userprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

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

        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        editEmail = findViewById(R.id.edit_email_address);
        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editAddressOne = findViewById(R.id.edit_address);
        editAddressTwo = findViewById(R.id.edit_address_line_two);
        editCity = findViewById(R.id.edit_city);
        editZip = findViewById(R.id.edit_zip_code);
        buttonRegister = findViewById(R.id.button_register);

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
                String zip = editZip.getText().toString();

                UserDetails userDetails = new UserDetails(username, password, email, firstname, lastname, addressone, addresstwo, city, zip);
                registerUser(userDetails);
            }
        });
    }


    private void registerUser(UserDetails user) {

    }


    class UserDetails {
        String username;
        String password;
        String email;
        String firstname;
        String lastname;
        String addressone;
        String addresstwo;
        String city;
        String zip;
        
        UserDetails(String username, String password, String email, String firstname, String lastname, String addressone, String addresstwo, String city, String zip) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.addressone = addressone;
            this.addresstwo = addresstwo;
            this.city = city;
            this.zip = zip;
        }
    }
}
