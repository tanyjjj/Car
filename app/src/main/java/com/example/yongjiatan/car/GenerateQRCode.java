package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
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

public class GenerateQRCode extends AppCompatActivity {
    String DATE, TIME, LOCATION;

    String userid;
    EditText text;
    ImageView image;

    //temp
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        userid = getIntent().getExtras().getString("userid");
        image = (ImageView) findViewById(R.id.image);
        Button gen_btn = (Button) findViewById(R.id.gen_btn);
//temp
        tv=(TextView)findViewById(R.id.display);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            //trigger when button clicked
            @Override
            public void onClick(View view) {
                BackgroundRun backgroundRun = new BackgroundRun();
                backgroundRun.execute(userid);



            }
        });

    }

    public void generateQR(String result) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(result, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //temp
        tv.setText( "Date: "+DATE+"\n Time: "+ TIME +"\n Location: "+ LOCATION);

    }

    class BackgroundRun extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            userid = params[0];
            String retrieve_url = "http://192.168.137.1/getdata.php";

            try {

                URL url = new URL(retrieve_url);
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
        protected void onPostExecute(String result)  {
           try {

               JSONArray reserve = new JSONArray(result);
               JSONObject c = null;

               for (int i =0;i < reserve.length();i++){
                   c = reserve.getJSONObject(i);
                   DATE = c.getString("date");
                   TIME = c.getString("time");
                   LOCATION = c.getString("parkingstructure");
                   String data = "Date: "+DATE+"\n Time: "+ TIME +"\n Location: "+ LOCATION;
                //   Toast.makeText(getApplicationContext(),DATE + TIME + LOCATION, Toast.LENGTH_SHORT).show();
                   generateQR(data);

               }

           } catch (JSONException e) {e.printStackTrace();}

           }
    }
}



