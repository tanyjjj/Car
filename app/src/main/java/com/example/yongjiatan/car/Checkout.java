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
    private TextView display, displayIn;
    String rid, timeout, time,checkin;
    Date checkinTime,checkoutTime;
    //  String resulttime;
    Calendar calender;
    // double costPerHour =3.00;
    //  double totalParkingFee;

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

        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
           checkinTime = format.parse(time);
           checkoutTime = format.parse(timeout);
            test = (TextView) findViewById(R.id.testing);
            test.setText("Crqrrqr:" + checkinTime+ "wqdqdqwd:" + checkoutTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void SubmitParking(View view) {
        BackgroundCheckOut backgroundWorker = new BackgroundCheckOut(this);
        backgroundWorker.execute(timeout, rid);
        Intent goToView = new Intent(this, Homepage.class);
        startActivity(goToView);
//totalParkingFee = costPerHour ×
    }

}