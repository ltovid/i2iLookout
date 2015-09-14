package com.app.i2i.lookout;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sherwin on 9/6/2015.
 * This Class performs the geolocation services and then
 * stores the location and address by sending a string to SendUsingGet.java
 * which does the transmission of data to the DB
 */
public class FetchAddressIntentService extends IntentService {
    public static final String SERVER_ADDRESS = "www.lookout-tt.com";
    private static final String TAG = "fetch-address-intent-service";
    /**
     * The receiver where results are forwarded from this service.
     */
    protected ResultReceiver mReceiver;

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public FetchAddressIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {


        String errorMessage = "";

        mReceiver = intent.getParcelableExtra(Constants.RECEIVER); //CONSTANTS declared in class CONSTANTS

        UserLocalStore userLocalStore;
        // Session class instance
        userLocalStore = new UserLocalStore(getApplicationContext());

        //check to see which user is logged in
        User loggedInUser = userLocalStore.getLoggedInUser();
        //check to see if there is a logged in user, if not redirect to signInActivity
        if (loggedInUser == null) {
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        }

        // Check if receiver was properly registered.
        if (mReceiver == null) {
            Log.wtf(TAG, "No receiver received. There is nowhere to send the results.");
            return;
        }

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);//CONSTANTS declared in class CONSTANTS

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if (location == null) {
            errorMessage = "no_location_data_provided";
            Log.wtf(TAG, errorMessage);
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
            return;
        }


        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.

        // The Geocoder used in this sample. The Geocoder's responses are localized for the given
        // Locale, which represents a specific geographical or linguistic region. Locales are used
        // to alter the presentation of information such as numbers or dates to suit the conventions
        // in the region they describe.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        Float speed = location.getSpeed();

        String SpeedInKM = (speed*3600/1000)+"";

        // Address found using the Geocoder.


        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "service_not_available";
            Log.e("fetch-address-intent", errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "invalid_lat_long_used";
            Log.e("fetch-address-intent", errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "no_address_found";
                Log.e("fetch-address-intent", errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread. The {@link android.location.address}
            // class provides other options for fetching address details that you may prefer
            // to use. Here are some examples:
            // getLocality() ("Mountain View", for example)
            // getAdminArea() ("CA", for example)
            // getPostalCode() ("94043", for example)
            // getCountryCode() ("US", for example)
            // getCountryName() ("United States", for example)*/
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));

            }

            String streetAddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            //SEND DATA TO STORE IN DB---------------------------------------------------------------------------------------------------------------------------------
            //Log.v("lat and lon", latitude+" "+longitude);
            //Log.v("store lat and lon", userLocalStore.getLastLatitude()+" "+userLocalStore.getLastLongitude());

            if (!latitude.equals(userLocalStore.getLastLatitude()) && !longitude.equals(userLocalStore.getLastLongitude())) {
                userLocalStore.setLocation(latitude, longitude);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                // formattedDate have current date/time

                //System.out.println("Current time => " + formattedDate);

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority(SERVER_ADDRESS)
                        .appendPath("addLocation.php")
                        .appendQueryParameter("username", loggedInUser.username)
                        .appendQueryParameter("device", loggedInUser.device)
                        .appendQueryParameter("latitude", latitude)
                        .appendQueryParameter("longitude", longitude)
                        .appendQueryParameter("d", formattedDate)
                        .appendQueryParameter("street", streetAddress)
                        .appendQueryParameter("city", city)
                        .appendQueryParameter("state", state)
                        .appendQueryParameter("country", country)
                        .build();


                String query = builder.toString();

                try {
                    Log.v("display", "in try: FetchIntentService");
                    //userLocalStore.setConnectionString(query);
                    SendUsingGet sendToDb = new SendUsingGet();
                    sendToDb.SendGetNoResponse(query); // Send to store in database, no response expected

                    userLocalStore.setUserLocationGroup(city);

                } catch (Exception e) {

                    //lblLocation.setText(latitude + ", " + longitude);
                }
            }//end of if(!latitude.equals(userLocalStore.getLastLatitude()) && !longitude.equals(userLocalStore.getLastLongitude()))
            //---------------------------------------------------------------------------------------------------------------------------------------------------------

            Log.i("fetch-address-intent", "address_found");
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }
    }

    /**
     * Sends a resultCode and message to the receiver.
     */
    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }
}
