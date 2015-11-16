package com.app.i2i.lookout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

public class SignUpActivity extends LoginActivity {
    Toolbar mToolbar;
    EditText etFName, etLName, etUsername, etPassword, etCity;

    UserLocalStore userLocalStore; //Shared Preference Name

    //this is the button to sign-in with email and password
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mToolbar = (Toolbar) findViewById(R.id.toolbarSignUp);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            // mToolbar.setNavigationIcon(R.drawable.abc_edit_text_material);
        }

        userLocalStore = new UserLocalStore(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        etFName = (EditText) findViewById(R.id.signUpName);
        etLName = (EditText) findViewById(R.id.signUp_Lastname);
        etUsername = (EditText) findViewById(R.id.signUp_email_address);
        etPassword = (EditText) findViewById(R.id.signIn_password);
        etCity = (EditText) findViewById(R.id.signUp_city_address);

        //This is the NEXT button on activity_sign_up.xml
        Button signup = (Button) findViewById(R.id.btn_sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_sign_up:
                        String firstName = etFName.getText().toString();
                        String lastName = etLName.getText().toString();
                        String userName = etUsername.getText().toString();
                        String password = etPassword.getText().toString();
                        String homeGroup = etCity.getText().toString();

                        String device = getUserDevice(getApplicationContext());

                        user = new User(firstName, lastName, userName, password, device);

                        user.homeGroup = homeGroup;


                        registerUser(user);
                        break;
                }
            }

        });
    }


    public void signIn(View view) {
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    public String getUserDevice(Context context) {

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mngr.getDeviceId();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerUser(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

            }
        });


        //ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser != null) {
                    if (returnedUser.status != 3) {
                        //Intent joinGroup = new Intent(SignUpActivity.this, com.app.i2i.lookout.JoinCommunityActivity.class);
                        Toast.makeText(getApplicationContext(), "Hello " + returnedUser.firstName, Toast.LENGTH_LONG).show();
                        Intent mainActivity = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                    } else {
                        Toast.makeText(getApplicationContext(), "User is already registered, please sign in", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error on registration, Please try again", Toast.LENGTH_LONG).show();
                }

            }
        });


    }//end of registerUser
}
