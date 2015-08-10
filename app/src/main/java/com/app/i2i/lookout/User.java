package com.app.i2i.lookout;


public class User {

    String firstName, lastName, username, password;


    public User(String fName, String lName, String username, String password) {
        this.firstName = fName;
        this.lastName = lName;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this("", "", username, password);
    }
}
