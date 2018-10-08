package com.example.yongjiatan.car;

import android.app.AlertDialog;
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

public class BackgroundCheckOut extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String rid,timeout;

    BackgroundCheckOut(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        timeout = params[0];
         rid = params[1];
        String updatetime_url = "http://192.168.137.1/updatetime.php";

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



    /**    else if (type.equals("ParkingSpot")) {
            try {
                String userid = params[1];
                String password = params[2];
                String name = params[3];
                String contactno = params[4];
                String dateofbirth = params[5];
                String email = params[6];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("contactno", "UTF-8") + "=" + URLEncoder.encode(contactno, "UTF-8") + "&"
                        + URLEncoder.encode("dateofbirth", "UTF-8") + "=" + URLEncoder.encode(dateofbirth, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null) {
                    result += line;
                }
                bufferedReader.close();;
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        return null; **/


  /**  @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    } **/
    @Override
    protected void onPostExecute(String result){
        if(result.toString().equals("Insert successful"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();
            Intent intent = new Intent (context, Homepage.class);
            context.startActivity(intent);

        }

       /** else if  (result.toString().equals("Registration successful.Thank you for registering"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();
            Intent intent = new Intent (context, Login.class);
            context.startActivity(intent);
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        } **/
    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

}

