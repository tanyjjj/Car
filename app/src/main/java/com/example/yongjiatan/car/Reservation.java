package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Reservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
    }

    public void JumpToParkingStructure(View view) {
        Intent goToParkingStructure = new Intent(this,ParkingStructure.class);

        startActivity(goToParkingStructure);
    }
    public void JumpToSelectTime(View view) {
        Intent goToSelectTime = new Intent(this,SelectTime.class);

        startActivity(goToSelectTime);
    }
}