package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

public class BackgroundUpdateSpot extends AsyncTask<String,Void,String> {
        Context context;
        String rid,time,userid,parkingspot,date;
        BackgroundUpdateSpot(Context ctx) {
        context = ctx;
        }
@Override
protected String doInBackground(String... params) {
   rid = params[0];
   time= params[1];
   userid= params[2];
   parkingspot= params[3];
   date=params[4];
        String updatespot_url = "http://192.168.137.1/updateSpotusingRid.php";
        try {
        //Create a URL object holding our url
        URL url = new URL(updatespot_url);
        //Create a connection
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = URLEncoder.encode("parkingspot", "UTF-8") + "=" + URLEncoder.encode(parkingspot, "UTF-8")+ "&"
        + URLEncoder.encode("rid", "UTF-8") + "=" + URLEncoder.encode(rid, "UTF-8");
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
    Intent intent=new Intent(context,IService.class);
   intent.putExtra("rid", rid);
    intent.putExtra("time", time);
   intent.putExtra("userid", userid);
   intent.putExtra("parkingspot", parkingspot);
    intent.putExtra("date", date);
    context.startService(intent);
   /** Intent intent = new Intent (context,Homepage.class);
    intent.putExtra("rid", rid);
    intent.putExtra("time", time);
    intent.putExtra("userid", userid);
    intent.putExtra("parkingspot", parkingspot);
    intent.putExtra("date", date);
    context.startActivity(intent); **/


        }

        }