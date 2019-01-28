package com.landychan.userprofile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    HashMap<String, UserDetails> usersMap;
    DatePickerDialog datePickerDialog;
    UserDetails user;
    Calendar calendar;
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
    EditText editBirthdate;
    Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        calendar = Calendar.getInstance();

        gson = new Gson();

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("edituser", false);

        if(isEditMode) {
            String serializedUser = intent.getStringExtra("userdetails");
            user = gson.fromJson(serializedUser, UserDetails.class);
        }

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

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
        editBirthdate = findViewById(R.id.edit_birthdate);
        buttonRegister = findViewById(R.id.button_register);
        datePickerDialog = new DatePickerDialog(RegisterActivity.this, datePicker, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        if(isEditMode) {
            editUsername.setEnabled(false);
            buttonRegister.setText("Save");
            showUserDetails();
        }

        editBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(!datePickerDialog.isShowing())
                        datePickerDialog.show();
                }
            }
        });

        editBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!datePickerDialog.isShowing())
                    datePickerDialog.show();
            }
        });

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
                String birthdate = editBirthdate.getText().toString();

                UserDetails newUser = new UserDetails(username, password, email, firstname, lastname, addressone, addresstwo, city, state, zip, birthdate);

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
            editBirthdate.setText(user.birthdate);
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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editBirthdate.setText(sdf.format(calendar.getTime()));
    }

}
