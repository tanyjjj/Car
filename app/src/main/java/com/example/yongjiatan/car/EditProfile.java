package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class EditProfile extends AppCompatActivity {
    EditText  Password, Name, ContactNo, DateOfBirth, Email;
    String userid;
    public static final String registerpreference = "MyPrefs";
    public static final String USERNAME = "usernameKey";
    public static final String NAME = "nameKey";
    public static final String PASSWORD = "passwordKey";
    public static final String EMAIL = "emailKey";
    public static final String CONTACTNO = "contactNoKey";
    public static final String DOB = "DOBKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userid = getIntent().getExtras().getString("userid");

        Password = (EditText) findViewById(R.id.Upassword);
        Name = (EditText) findViewById(R.id.Uname);
        ContactNo = (EditText) findViewById(R.id.Ucontactno);
        DateOfBirth = (EditText) findViewById(R.id.Udate);
        Email = (EditText) findViewById(R.id.Uemail);

        String MPassword = Password.getText().toString();
        String MName = Name.getText().toString();
        String MContactno = ContactNo.getText().toString();
        String MDateofbirth = DateOfBirth.getText().toString();
        String MEmail = Email.getText().toString();
      //  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences = getSharedPreferences("Edit",0);
        String a = sharedPreferences.getString("USERNAME","password.getText().toString()");
        TextView b = (TextView)findViewById(R.id.b);
        b.setText(a);
    }

    public void OnUpdate(View view) {
        String MPassword = Password.getText().toString();
        String MName = Name.getText().toString();
        String MContactno = ContactNo.getText().toString();
        String MDateofbirth = DateOfBirth.getText().toString();
        String MEmail = Email.getText().toString();

        BackgroundUpdate backgroundWorker = new BackgroundUpdate(this);
        backgroundWorker.execute(userid, MPassword,MName,MContactno,MDateofbirth,MEmail );
    }

    private class BackgroundUpdate extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context context;
        BackgroundUpdate (Context ctx) { context = ctx; }
        @Override
        protected String doInBackground(String... params) {
            userid = params[0];
            String password = params[1];
            String name = params[2];
            String contactno = params[3];
            String dateofbirth = params[4];
            String email = params[5];
            String updateprofile_url = "http://192.168.137.1/updateprofile.php";

            try {

                URL url = new URL(updateprofile_url);
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
            return null;
        }

        @Override
        protected void onPreExecute(){
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Update Profile");
        }
        @Override
        protected void onPostExecute(String Result){
            if (Result.toString().equals("Your Profile Updated Successfully"))
            {
                alertDialog.setMessage(Result);
                alertDialog.show();

            }
            else
            {
                alertDialog.setMessage(Result);
                alertDialog.show();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

    }

}
