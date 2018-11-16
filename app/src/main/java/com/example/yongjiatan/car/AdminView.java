package com.example.yongjiatan.car;

import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class AdminView extends AppCompatActivity {
    ListView listR;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    String reservation = "http://192.168.137.1/adminviewR.php";
  CustomListView customListView;
    InputStream Input = null;
    String line = null;
    String result = null;
    String[] rid;
    String[] parkingstructure;
    String[] time;
    String[] date;
    String[] UID;
EditText search;
String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        search = (EditText) findViewById(R.id.tv1);
        listR = (ListView) findViewById(R.id.list);
 

       StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getData();
        customListView = new CustomListView(this, rid, parkingstructure, UID, time, date);
        listR.setAdapter(customListView);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
           // customListView.getFilter().filter(s.toString());
                AdminView.this.customListView.getFilter().filter(s);
           //    test =search.getText().toString().toLowerCase(Locale.getDefault());
            //    customListView.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



    private void getData()
    {

        try
        {
            URL url = new URL(reservation);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            Input = new BufferedInputStream(con.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(Input));
            StringBuilder sb=new StringBuilder();
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            Input.close();
            result=sb.toString();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            JSONArray arr= new JSONArray(result);
            JSONObject obj=null;
            rid = new String[arr.length()];
            parkingstructure = new String[arr.length()];
           time= new String[arr.length()];
            date = new String[arr.length()];
           UID = new String[arr.length()];
            for(int i=0;i<arr.length();i++){
                obj= arr.getJSONObject(i);
                rid[i]=obj.getString("rid");
                parkingstructure[i]=obj.getString("parkingstructure");
                time[i]=obj.getString("time");
                date[i]=obj.getString("date");
                UID[i]=obj.getString("UID");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
