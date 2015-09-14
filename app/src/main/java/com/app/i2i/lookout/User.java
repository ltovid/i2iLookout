package com.app.i2i.lookout;


public class User {

    String firstName, lastName, username, password, device, id, homeGroup;
    int status;


    public User(String fName, String lName, String username, String password, String device) {
        this.firstName = fName;
        this.lastName = lName;
        this.username = username;
        this.password = password;
        this.device = device;
        this.id ="";
        this.homeGroup="";
        this.status=0; //0 is no status, 1 is logged in, 2 is not logged in, 3 is already created but not logged in
    }

    public User(String username, String password) {
        this("", "", username, password, "");
        this.id ="";
        this.homeGroup="";
        this.status=0; //0 is no status, 1 is logged in, 2 is not logged in, 3 is already created but not logged in
    }

    public User(String fName, String lName, String username, String password, String device, String id) {
        this.firstName = fName;
        this.lastName = lName;
        this.username = username;
        this.password = password;
        this.device = device;
        this.id =id;
        this.homeGroup="";
        this.status=0; //0 is no status, 1 is logged in, 2 is not logged in, 3 is already created but not logged in
    }

    private void SetUserDevice(String device){

        this.device = device;
    }

    private String GetFirstName(){
        return this.firstName;
    }

    private String GetLastName(){
        return this.lastName;
    }

    private String GetUserName(){
        return this.username;
    }


}
