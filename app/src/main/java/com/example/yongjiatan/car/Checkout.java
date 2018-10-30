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
    private TextView display, displayIn,parking,timeD;
    String rid, timeout, time,checkin,parkingspot;
    Date checkinTime = null;
    Date checkoutTime = null;

    Calendar calender;
     double costPerHour =3.00;
     double totalParkingFee,totalhour,totalhourextra;

    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        rid = getIntent().getExtras().getString("rid");
        timeout = getIntent().getExtras().getString("timeout");
        time = getIntent().getExtras().getString("time");
        checkin = getIntent().getExtras().getString("checkin");
        parkingspot = getIntent().getExtras().getString("parkingspot");

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
       long diffhour= (diff / (60*60*1000));
        long diffmin = (diff / (60*1000)%60);
        totalhour = (diffhour)% 60;


        if(totalhour<=0){
totalParkingFee = costPerHour;
        } else if (totalhour >=10){
            totalParkingFee = 25;
        } else if (totalhour >=24){
            totalParkingFee = 50
            ;
        }else if (diffmin>30) {
            totalhourextra = totalhour +1;
            totalParkingFee = totalhourextra*costPerHour;
        }
        else {
            totalParkingFee = totalhour*costPerHour;
        }
        timeD  = (TextView) findViewById(R.id.timedisplay);
       timeD.setText("Total parking time:"+diffhour+"hour & "+diffmin+"minutes" );
       parking = (TextView) findViewById(R.id.parking_ID);
       parking.setText("Total parking fee:"+ "RM" + totalParkingFee );

    }


    public void SubmitParking(View view) {
        Backgroundout backgroundWorker = new Backgroundout(this);
        backgroundWorker.execute(timeout, rid,parkingspot);

    }

}