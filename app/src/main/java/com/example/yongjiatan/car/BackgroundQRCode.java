package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

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

public class BackgroundQRCode extends AsyncTask<String,Void,String> {
    Context context;
    String rid,time;
    String DATE, TIME, LOCATION,data;

    BackgroundQRCode (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        rid = params[0];
       time = params[1];
        String getdata_url = "http://192.168.137.1/getdata.php";

        try {

            URL url = new URL(getdata_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("rid", "UTF-8") + "=" + URLEncoder.encode(rid, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            ;
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
        try {

            JSONObject c = new JSONObject(result);


            for (int i = 0; i < c.length(); i++) {
                DATE = c.getString("date");
                TIME = c.getString("time");
                LOCATION = c.getString("parkingstructure");
                data = "Date: " + DATE + "\n Time: " + TIME + "\n Location: " + LOCATION;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, GenerateQRCode.class);
        intent.putExtra("data", data);
        intent.putExtra("rid", rid);
        intent.putExtra("time", time);
       context.startActivity(intent);
    }
}

