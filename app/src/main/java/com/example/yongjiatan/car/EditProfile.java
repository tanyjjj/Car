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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    EditText Password, Name, ContactNo, DateOfBirth, Email;
    String userid;
    String p, n, cn, d, e;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userid = getIntent().getExtras().getString("userid");
        p = getIntent().getExtras().getString("password");
        n = getIntent().getExtras().getString("name");
        cn = getIntent().getExtras().getString("contactno");
        d = getIntent().getExtras().getString("dateofbirth");
        e = getIntent().getExtras().getString("email");

        Password = (EditText) findViewById(R.id.Upassword);
        Password.setText(p);
        Name = (EditText) findViewById(R.id.Uname);
        Name.setText(n);
        ContactNo = (EditText) findViewById(R.id.Ucontactno);
        ContactNo.setText(cn);
        DateOfBirth = (EditText) findViewById(R.id.Udate);
        DateOfBirth.setText(d);
        Email = (EditText) findViewById(R.id.Uemail);
        Email.setText(e);

        if(!validatePassword(Password.getText().toString())||Password.getText().toString().length()<8){
            Password.setError("Password must be at least 8 characters,one number,one special character and upper case letter ");
            Password.requestFocus();
        }
        if(!validateEmail(Email.getText().toString())) {
            Email.setError("Email must have valid email format.Please try again!");
            Email.requestFocus();
        }
    }

    public void OnUpdate(View view) {
        if ((!validatePassword(Password.getText().toString()) || Password.getText().toString().length() < 8)
                && (!validateEmail(Email.getText().toString()))) {
            Toast.makeText(getApplicationContext(), "Please enter valid format", Toast.LENGTH_SHORT).show();
        } else {
            String MPassword = Password.getText().toString();
            String MName = Name.getText().toString();
            String MContactno = ContactNo.getText().toString();
            String MDateofbirth = DateOfBirth.getText().toString();
            String MEmail = Email.getText().toString();

            BackgroundUpdate backgroundWorker = new BackgroundUpdate(this);
            backgroundWorker.execute(userid, MPassword, MName, MContactno, MDateofbirth, MEmail);
        }
    }
    protected boolean validatePassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    private class BackgroundUpdate extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;
        Context context;

        BackgroundUpdate(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String updateprofile_url = "http://192.168.137.1/updateprofile.php";
            try {
                userid = params[0];
                String password = params[1];
                String name = params[2];
                String contactno = params[3];
                String dateofbirth = params[4];
                String email = params[5];
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
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Update Profile");
        }

        @Override
        protected void onPostExecute(String Result) {
            if (Result.toString().equals("Your Profile has been successfully updated~")) {

                Intent intent = new Intent (EditProfile.this, Homepage.class);
                context.startActivity(intent);
            } else
            {
                alertDialog.setMessage(Result);
                alertDialog.show();
          //      alertDialog.setMessage("PLease enter valid input!!");
            }
        }
    }
}
