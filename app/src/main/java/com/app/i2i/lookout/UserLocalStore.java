package com.app.i2i.lookout;

import android.content.Context;
import android.content.SharedPreferences;


//Created by sherwin on 29/6/2015.


public class UserLocalStore {

    //declare a file name to save shared preference, this is where the user info is host locally
    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        //the context is saying that the activity using this LocalStore will instantiate
        //name of the file declared above
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("fName", user.firstName);
        userLocalDatabaseEditor.putString("lName",user.lastName);
        userLocalDatabaseEditor.putString("username", user.username);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("device", user.device);
        userLocalDatabaseEditor.putString("id", user.id);
        userLocalDatabaseEditor.apply();
    }

    public void setConnectionString(String conn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("conn", conn);
        userLocalDatabaseEditor.apply();
    }

    public String getConnectionString()
    {
        return userLocalDatabase.getString("conn","");
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.apply();
    }

    public void setLocation(String Latitude, String Longitude) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("Latitude", Latitude);
        userLocalDatabaseEditor.putString("Longitude", Longitude);
        userLocalDatabaseEditor.apply();
    }

    public String getLastLatitude()
    {
        return userLocalDatabase.getString("Latitude","");
    }

    public String getLastLongitude()
    {
        return userLocalDatabase.getString("Longitude","");
    }

    public String getfName()
    {
        return userLocalDatabase.getString("fName","");
    }

    public String getUserDevice()
    {
        String device = userLocalDatabase.getString("device", "");
        return device;
    }

    public String getUserId()
    {
        return userLocalDatabase.getString("id","");
    }

    public void setUserHomeGroup(String homeGroup)
    {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("homeGroup", homeGroup);
        userLocalDatabaseEditor.apply();
    }



    public String getUserHomeGroup()
    {
        return userLocalDatabase.getString("homeGroup","");
    }

    public void setUserLocationGroup(String locationGroup)
    {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("locationGroup", locationGroup);
        userLocalDatabaseEditor.apply();
    }



    public String getUserLocationGroup()
    {
        return userLocalDatabase.getString("locationGroup","");
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.apply();
    }


    public User getLoggedInUser() {
        if (!userLocalDatabase.getBoolean("loggedIn", false)) {
            return null;
        }

        String fName = userLocalDatabase.getString("fName", "");
        String lName = userLocalDatabase.getString("lName", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String device = userLocalDatabase.getString("device","");
        String id = userLocalDatabase.getString("id","");


        return new User(fName, lName, username, password, device, id);
    }
}
