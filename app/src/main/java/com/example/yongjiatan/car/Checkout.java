package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import java.util.Date;
public class Checkout extends AppCompatActivity {
    private TextView display, displayIn,parking;
    String rid, timeout, time,checkin;
    Date checkinTime = null;
    Date checkoutTime = null;

    Calendar calender;
     double costPerHour =3.00;
     double totalParkingFee,totalhour;

    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        rid = getIntent().getExtras().getString("rid");
        timeout = getIntent().getExtras().getString("timeout");
        time = getIntent().getExtras().getString("time");
        checkin = getIntent().getExtras().getString("checkin");

        display = (TextView) findViewById(R.id.displaytext);
        display.setText("Check Out Time:" + timeout);

        displayIn = (TextView) findViewById(R.id.displaycheckin);
        displayIn.setText("Check In Time:" + time);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        try {
           checkinTime = format.parse(time);
           checkoutTime = format.parse(timeout);

        } catch (ParseException e) {
            e.printStackTrace();
        }
 long diff= checkoutTime.getTime() - checkinTime.getTime();
        long diffmin = diff / (60*60*1000);
        totalhour = diffmin% 60;
        if(totalhour<=0){
totalParkingFee = costPerHour;
        } else {
            totalParkingFee = totalhour*costPerHour;
        }

       parking = (TextView) findViewById(R.id.parking_ID);
       parking.setText("Total parking fee:"+ "RM" + totalParkingFee);
    }


    public void SubmitParking(View view) {
        BackgroundCheckOut backgroundWorker = new BackgroundCheckOut(this);
        backgroundWorker.execute(timeout, rid);
        Intent goToView = new Intent(this, Homepage.class);
        startActivity(goToView);

    }

}