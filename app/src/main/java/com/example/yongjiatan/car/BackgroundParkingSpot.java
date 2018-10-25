package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundParkingSpot extends AsyncTask<String,Void,String> {

        Context context;
String test;
    JSONObject id;
        String parkingspot, rid,time,userid;
        BackgroundParkingSpot (Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
           parkingspot = params[0];
           rid = params[1];
          userid= params[2];
           time = params[3];
            String parkingspot_url = "http://192.168.137.1/parkingspot.php";

            try {
                //Create a URL object holding our url
                URL url = new URL(parkingspot_url);
                //Create a connection
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("parkingspot", "UTF-8") + "=" + URLEncoder.encode(parkingspot, "UTF-8") ;
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String[] test;
            try {

                JSONArray c = new JSONArray(result);



                for (int i = 0; i < c.length(); i++) {
                    JSONObject j = c.getJSONObject(i);
                    Toast.makeText(context, j.getString("parkingspot"),Toast.LENGTH_SHORT).show();

             //      test = j.getString("parkingspot");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

       Intent intent = new Intent (context,Homepage.class);
            intent.putExtra("rid", rid);
          intent.putExtra("time", time);
          intent.putExtra("userid", userid);
            intent.putExtra("parkingspot", parkingspot);
            context.startActivity(intent);


        }

}
