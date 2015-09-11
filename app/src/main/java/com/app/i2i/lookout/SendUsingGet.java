package com.app.i2i.lookout;

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

/**
 * Created by sherwin on 8/28/2015.
 */
public class SendUsingGet {

    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    UserLocalStore userLocalStore;


    public SendUsingGet() {
    }

    public void SendGetNoResponse(String url){
        new SendGetNoResponseAsyncTask().execute(url);
    }


    public class SendGetNoResponseAsyncTask extends AsyncTask<String,Void, Void> {

        protected Void doInBackground(String... urls)
        {
            try {

                URL url= new URL(urls[0]);

                Log.v("FROM SendUsingGet", url.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(CONNECTION_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                String query = url.toString();
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode = conn.getResponseCode();

                String response = "";
                String message;
                Log.v("responseCode", responseCode+"");
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }

                    Log.v("response", response);

                    JSONObject jObject = new JSONObject(response);

                    if (jObject.length() != 0) {

                        message = jObject.getString("response_message");
                        //Log.v(query + " message", message);
                    } else
                        Log.v("FROM " + query, "No JSON message received");
                }
            } catch (Exception e) {

                Log.v("FROM SendUsingGet", e.toString());
                return null;
            }
            return null;
        }
    }

/*
    public JSONObject SendGetResponse(URL url) {
        JSONObject jObject;

        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String query = this.getUrl();

            Log.v("FROM" + url.toString(), query);

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
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

                Log.v("RESPONSE", response);

                jObject = new JSONObject(response);

                return jObject;

            } // End of if (responseCode == HttpsURLConnection.HTTP_OK)
        } catch (Exception e) {
            Log.v("FROM" + url.toString(), e.toString());
        } // End of Catch
        jObject= new JSONObject();
        return jObject;
    } // End of public JSONObject SendGetResponse(String query, URL url)
*/

    protected void onPostExecute(String result) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
} // End of public class SendUsingGet
