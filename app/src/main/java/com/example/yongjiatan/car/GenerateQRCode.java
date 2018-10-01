package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
    Context context;
    String userid;
    EditText text;
    Button gen_btn;
    ImageView image;
    String text2Qr;
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
           image = (ImageView) findViewById(R.id.image);
           new BackgroundRun().execute();
        gen_btn.setOnClickListener(new View.OnClickListener() {
            //trigger when button clicked
            @Override
            public void onClick(View view) {
                //put the string result here
                text2Qr = gen_btn.toString().trim();
                //put backgroundworker

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class BackgroundRun extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            userid = params[1];
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
        protected void onPostExecute(String result)  {
           try {

               JSONObject root = new JSONObject(result);
               JSONObject obj = root.getJSONObject("");
               DATE = obj.getString("date");
               TIME = obj.getString("time");
               LOCATION = obj.getString("parkingstructure");
                data = "Date: "+DATE+"\n Time= "+ TIME +"\n Location "+ LOCATION;;
           } catch (JSONException e) {e.printStackTrace();}

           }
         /**  JSONObject obj = new JSONObject(result);
            //  JSONObject obj = root.getJSONObject("");
            DATE = obj.getString("parkingstructure");
            TIME = obj.getString("time");
            LOCATION = obj.getString("date");
**/
        }
        // private void loadIntoTextView(String json) throws JSONException {
        //   JSONObject jsonObject = new JSONObject(json);

        //   JSONObject obj = jsonObject.getJSONObject();
        // DATE = obj.getString("parkingstructure");
        // TIME = obj.getString("time");
        //  LOCATION  = obj.getString("date");
        //make it become into result,3 in result
    }

    //context.name

