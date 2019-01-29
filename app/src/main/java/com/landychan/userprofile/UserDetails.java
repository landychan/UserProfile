package com.landychan.userprofile;

class UserDetails {

    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String addressOne;
    String addressTwo;
    String city;
    String state;
    String zip;
    String birthdate;

    UserDetails(String username, String password, String email, String firstname, String lastname, String addressone, String addresstwo, String city, String state, String zip, String birthdate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.addressOne = addressone;
        this.addressTwo = addresstwo;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.birthdate = birthdate;
    }
}
