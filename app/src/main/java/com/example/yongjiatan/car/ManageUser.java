package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ManageUser extends AppCompatActivity {
EditText searchID;
TextView getUserID,getPassword,getName,getContactNo,getDOB,getEmail;
ImageView go;
String userid,type;
    String u,p, n, cn, d, e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        searchID = (EditText) findViewById(R.id.searchBtn);
        getUserID= (TextView)findViewById(R.id.getU);
        getPassword= (TextView)findViewById(R.id.getP);
        getName= (TextView)findViewById(R.id.getN);
        getContactNo= (TextView)findViewById(R.id.getCN);
        getDOB= (TextView)findViewById(R.id.getDOB);
        getEmail= (TextView)findViewById(R.id.getE);

       go=(ImageView)findViewById(R.id.goBtn);

    }
    public void OnGo(View view) {
        userid = searchID.getText().toString();
        BackgroundRun runner = new BackgroundRun();
        runner.execute(userid);
    }
    public void DeleteUser(View view) {
        userid = searchID.getText().toString();
        BackgroundDeleteUser runner = new BackgroundDeleteUser(this);
        runner.execute(userid);
    }

    private class BackgroundRun extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            userid = params[0];
            String finduser_url = "http://192.168.137.1/adminfinduser.php";

            try {

                URL url = new URL(finduser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
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
                    u = c.getString("userid");
                    p = c.getString("password");
                    n = c.getString("name");
                    cn = c.getString("contactno");
                    d = c.getString("dateofbirth");
                    e = c.getString("email");

                    getUserID.setText("User ID:" + u);
                    getPassword.setText("Password:" + p);
                    getName.setText("Name:" + n);
                    getContactNo.setText("Contact Number:" + cn);
                    getDOB.setText("Date of Birth:" + d);
                    getEmail.setText("Email:" + e);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
