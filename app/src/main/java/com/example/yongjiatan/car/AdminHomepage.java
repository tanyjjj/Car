package com.example.yongjiatan.car;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminHomepage extends AppCompatActivity {
ImageView viewR,manageU,modifyS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
      viewR = (ImageView) findViewById(R.id.view);
      manageU= (ImageView) findViewById(R.id.manage);
      modifyS= (ImageView) findViewById(R.id.modify);
       viewR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToView = new Intent(AdminHomepage.this, AdminView.class);
                startActivity(goToView);
            }
    });
        manageU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToManage = new Intent(AdminHomepage.this, ManageUser.class);
                startActivity(goToManage);
            }
        });
        modifyS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToModify = new Intent(AdminHomepage.this,AdminModifyStatus.class);
                startActivity(goToModify);
            }
        });
    }

}
