package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class BackgroundSelectProfile  extends AsyncTask<String,Void,String> {
String p,n,cn,d,e;
    Context context;
    String userid;
    BackgroundSelectProfile(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        userid = params[0];
        String selectprofile_url = "http://192.168.137.1/selectprofile.php";

        try {

                URL url = new URL(selectprofile_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&";

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
    protected void onPostExecute(String Result) {
        try {

            JSONObject c = new JSONObject(Result);


            for (int i = 0; i < c.length(); i++) {
                p = c.getString("password");
                n = c.getString("name");
                cn = c.getString("contactno");
                d = c.getString("dateofbirth");
                e = c.getString("email");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent (context, EditProfile.class);
        intent.putExtra("userid", userid);
        intent.putExtra("password", p);
        intent.putExtra("name", n);
        intent.putExtra("contactno", cn);
        intent.putExtra("dateofbirth", d);
        intent.putExtra("email", e);
        context.startActivity(intent);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}




