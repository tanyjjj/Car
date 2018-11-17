package com.example.yongjiatan.car;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class AdminModifyStatus extends AppCompatActivity {
RadioGroup radioGroup;
RadioButton radioButton,radio1,radio2;
ImageView search;
EditText searchID;
String changeresult,spot,status;
TextView respond;
String searchkey;
    int radioID;
    Button change_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_status);
        searchID = (EditText) findViewById(R.id.searchBar);
        radioGroup= (RadioGroup)findViewById(R.id.radioG);
        search=(ImageView)findViewById(R.id.searchBtn);
        respond=(TextView)findViewById(R.id.resultText);
        change_Btn=(Button)findViewById(R.id.changeBtn);
        radio1 = (RadioButton)findViewById(R.id.radioO);
        radio2 = (RadioButton)findViewById(R.id.radioE);
          change_Btn.setOnClickListener(new View.OnClickListener() {
            //trigger when button clicked
            @Override
            public void onClick(View view) {
                 BackgroundUpdateStatus runner = new BackgroundUpdateStatus(AdminModifyStatus.this);
                   runner.execute(searchkey,changeresult);
            }
        });
    }
    public void OnSearch(View view) {
        searchkey = searchID.getText().toString();
        BackgroundRun runner = new BackgroundRun();
        runner.execute(searchkey);
    }
    public void rbclick(View v){
      radioID = radioGroup.getCheckedRadioButtonId();
      radioButton=(RadioButton)findViewById(radioID);
       changeresult=  radioButton.getText().toString();
    }


 private class BackgroundRun extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
     searchkey = params[0];
        String finduser_url = "http://192.168.137.1/adminparkingstatus.php";
        try {

            URL url = new URL(finduser_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("parkingspot", "UTF-8") + "=" + URLEncoder.encode(searchkey, "UTF-8");
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
                spot = c.getString("parkingspot");
                status = c.getString("status");
                respond.setText("Parking Spot:" + spot + "\n" + "Parking Status:" + status);
               if (status.equals("Occupied")) {
                    radio1.setChecked(true);
                } else {
                    radio2.setChecked(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
}

