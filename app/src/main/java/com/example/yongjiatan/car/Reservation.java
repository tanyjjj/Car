package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Reservation extends AppCompatActivity {
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = getIntent().getExtras().getString("userid");
        setContentView(R.layout.activity_reservation);
    }

    public void JumpToParkingStructure(View view) {
        Intent goToParkingStructure;
        goToParkingStructure = new Intent(this, ParkingStructure.class);
        goToParkingStructure.putExtra("userid", userid);
        startActivity(goToParkingStructure);

    }
}