package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class Backgroundout extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String rid, timeout,userid;
    String parkingspot,totalParkingFee2;
String newparkingspot=null;
String newrid= null;
    Backgroundout(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        timeout = params[0];
        rid = params[1];
        parkingspot = params[2];
        userid = params[3];
        totalParkingFee2 = params[4];
        String updatetime_url = "http://192.168.137.1/updatetime.php";
        String updatestatus_url = "http://192.168.137.1/parkingspotcheckout.php";
        try {
            URL url = new URL(updatetime_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("timeout", "UTF-8") + "=" + URLEncoder.encode(timeout, "UTF-8") + "&"
                    + URLEncoder.encode("rid", "UTF-8") + "=" + URLEncoder.encode(rid, "UTF-8");
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
            inputStream.close();
            httpURLConnection.disconnect();

            URL statusurl = new URL(updatestatus_url);
            //Create a connection
            httpURLConnection = (HttpURLConnection) statusurl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            outputStream = httpURLConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            post_data = URLEncoder.encode("parkingspot", "UTF-8") + "=" + URLEncoder.encode(parkingspot, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            result = "";
            //   line = "";
            while ((line = bufferedReader.readLine()) != null) {
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
              Intent intent = new Intent(context, PaymentPage.class);
        intent.putExtra("rid", newrid);
        intent.putExtra("parkingspot", newparkingspot);
        intent.putExtra("userid", userid);
        intent.putExtra("totalParkingFee2", totalParkingFee2);
            context.startActivity(intent);

    }
}


