package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.yongjiatan.car.Homepage;

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

public class BackgroundWorkerParkingStructure extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String time,userid;
    BackgroundWorkerParkingStructure (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String text = params[0];
        time = params[1];
        String date = params[2];
        userid = params[3];

        String parkingstructure_url = "http://192.168.137.1/parkingstructure.php";

        try {
            //Create a URL object holding our url
            URL url = new URL(parkingstructure_url);
            //Create a connection
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("parkingstructure", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8") + "&"
                    + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")+ "&"
                    + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&"
                    + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
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
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Your Reservation ID : ");

    }
    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        super.onPostExecute(result);
        // Intent intent = new Intent (context, SelectParkingSpace.class);
        Intent intent = new Intent (context, SelectParkingSpace.class);
        intent.putExtra("rid", result);
        intent.putExtra("time", time);
        intent.putExtra("userid",userid);
        context.startActivity(intent);

    }
    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}
