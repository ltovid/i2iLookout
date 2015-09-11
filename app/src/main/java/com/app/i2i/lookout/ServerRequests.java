package com.app.i2i.lookout;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/*Created by Sherwin
on 29/06/2015*/

public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "www.lookout-tt.com";

    UserLocalStore userLocalStore; //Shared Preference Name


    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");



    }

    public void storeUserDataInBackground(User user,
                                          GetUserCallback userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallBack).execute();
    }



    /**
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }


        
		@Override
        protected Void doInBackground(Void... params) {
            Log.v("IN doInBackGround", "1");
            try {

                String userHomeGroup = user.homeGroup;

                String OSVERSION = android.os.Build.VERSION.RELEASE; //OS version deviceVersion on DB
                String MODEL = android.os.Build.MODEL; //model of device deviceModel on DB
                String BRAND = android.os.Build.BRAND; //deviceBrand
                String DISPLAY = android.os.Build.DISPLAY;//deviceDisplay on DB
                String MANUFACTURER = android.os.Build.MANUFACTURER; //deviceManufacturer on DB
                String SERIAL = android.os.Build.SERIAL; // deviceSerial on DB


                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority(SERVER_ADDRESS)
                        .appendPath("Register.php")
                        .appendQueryParameter("firstName", user.firstName)
                        .appendQueryParameter("lastName", user.lastName)
                        .appendQueryParameter("username", user.username)
                        .appendQueryParameter("password", BCrypt.hashpw(user.password, BCrypt.gensalt())) //hash the password before sending to store in DB
                        .appendQueryParameter("device", user.device)
                        .appendQueryParameter("deviceVersion", OSVERSION)
                        .appendQueryParameter("deviceModel", MODEL)
                        .appendQueryParameter("deviceBrand", BRAND)
                        .appendQueryParameter("deviceDisplay", DISPLAY)
                        .appendQueryParameter("deviceManufacturer", MANUFACTURER)
                        .appendQueryParameter("deviceSerial", SERIAL)
                        .appendQueryParameter("homeGroup", userHomeGroup)
                        .build();
                Log.v("REGISTER.PHP addr", builder.toString());

                String query = builder.toString();

                URL url = new URL(query);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(CONNECTION_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                Log.v("REGISTER.PHP query", query);

               OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode=conn.getResponseCode();

                String response="";
                String message;

                Log.v("responseCode", responseCode+"");
                Log.v("HTTP_OK", HttpsURLConnection.HTTP_OK+"");


                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }

                    Log.v("REGISTER.PHP response", response);

                    JSONObject jObject = new JSONObject(response);

                    if (jObject.length() != 0){

                        message = jObject.getString("response_message");
                        if(!message.equals("The user already exist"))
                            user.id=message;
                        else
                        {
                            user.status=3;  // Set user status to 3 which is created but not logged in, in this case the user already exist
                        }
                        Log.v("REGISTER.PHP message", message);
                    }
                    else
                        Log.v("FROM REGISTER.PHP", "No JSON message received");



                }
                else {
                    response = responseCode+"";
                    Log.v("httpError", response);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                Log.v("ERRORS", e.toString());
            }

            return null;
        }//END OF Void doInBackground(Void... params)
		
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);
        }

    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            User returnedUser = null;
            Log.v("do in background ", "2");

            try {
                //-------------------------------------------------------

                String OSVERSION = android.os.Build.VERSION.RELEASE; //OS version deviceVersion on DB
                String MODEL = android.os.Build.MODEL; //model of device deviceModel on DB
                String BRAND = android.os.Build.BRAND; //deviceBrand
                String DISPLAY = android.os.Build.DISPLAY;//deviceDisplay on DB
                String MANUFACTURER = android.os.Build.MANUFACTURER; //deviceManufacturer on DB
                String SERIAL = android.os.Build.SERIAL; // deviceSerial on DB


                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority(SERVER_ADDRESS)
                        .appendPath("FetchUserData.php")
                        .appendQueryParameter("username", user.username) //search by username
                        .appendQueryParameter("device", user.device)
                        .appendQueryParameter("deviceVersion", OSVERSION)
                        .appendQueryParameter("deviceModel", MODEL)
                        .appendQueryParameter("deviceBrand", BRAND)
                        .appendQueryParameter("deviceDisplay", DISPLAY)
                        .appendQueryParameter("deviceManufacturer", MANUFACTURER)
                        .appendQueryParameter("deviceSerial", SERIAL)
                        .build();
                Log.v("FetchUserData.php addr", builder.toString());

                String query = builder.toString();

                URL url = new URL(builder.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(CONNECTION_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                Log.v("FetchUserData.php query", query);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode=conn.getResponseCode();

                //-------------------------------------------------------

                String response="";
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }

                    Log.v("RESPONSE", response);

                    JSONObject jObject = new JSONObject(response);

                    if (jObject.length() != 0){
                        Log.v("happened", "2");
                        String id = jObject.getString("id");
                        String pword = jObject.getString("password");
                        String fName = jObject.getString("firstName");
                        String lName = jObject.getString("lastName");

                       // Log.v("returned pword", pword);
                       // Log.v("stored pword", user.password);


                        Log.v("user.password 2", user.password);
                        Log.v("retrievd pass 2", pword);
                        if (BCrypt.checkpw(user.password, pword.trim())) { //compare plain text password (user.password) with one retrieved from DB

                            returnedUser = new User(fName, lName, user.username,
                                    pword, user.device); //create new User if plainText matches hashed password retrieved from DB
                            Log.v("created Fname", returnedUser.firstName);
                            returnedUser.status = user.status;  //user.status will be three if the user exist before this login, if response_message sent back from Register.php says The user already exist
                            user.id=id;
                            user.firstName=fName;
                            user.lastName=lName;
                            user.status = 1;
                        }
                        else {
                            returnedUser = null; //return null if hashed password retrieved from DB does not match plain text password stored in UserStore
                            Log.v("password ", "false");
                        }
                    }

                    if(returnedUser!=null ){
                        if(returnedUser.status!=3) {
                            userLocalStore.storeUserData(returnedUser);
                            userLocalStore.setUserLoggedIn(true);
                            storeUserDataInBackground(returnedUser, userCallBack);
                        }
                    }

                    if(user.status==3) {
                        returnedUser=user;
                    }

                }
                else {
                    response = responseCode+"";
                    Log.v("httpError", response);
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }

            if(returnedUser==null)
                Log.v("Returned User", "is null");

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
        }
    }
}

