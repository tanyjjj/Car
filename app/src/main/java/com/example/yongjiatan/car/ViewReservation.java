package com.example.yongjiatan.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewReservation extends AppCompatActivity {
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);

        userid = getIntent().getExtras().getString("userid");
    }
}
