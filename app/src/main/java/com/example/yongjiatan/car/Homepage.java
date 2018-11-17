package com.example.yongjiatan.car;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Homepage extends AppCompatActivity {
    String userid,rid,time,checkin,parkingspot;
    Calendar calender;
    SimpleDateFormat simpleDateFormat;
    String timeout,date,currentdate;
    TextView Text;
    AlertDialog alertDialog;
    ImageView ImageCheckIn;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        rid = getIntent().getExtras().getString("rid");
        time = getIntent().getExtras().getString("time");
        userid = getIntent().getExtras().getString("userid");
        date = getIntent().getExtras().getString("date");
        checkin = getIntent().getExtras().getString("checkin");
        parkingspot = getIntent().getExtras().getString("parkingspot");

        Text = (TextView) findViewById(R.id.checkoutText);
        ImageCheckIn=(ImageView)findViewById(R.id.checkinImage);
        final TextView scan = (TextView) findViewById(R.id.scanText);
        final Activity activity = this;
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        if(checkin==null){
            Text.setVisibility(View.INVISIBLE);
           ImageCheckIn.setVisibility(View.INVISIBLE);
        }

        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      currentdate = simpleDateFormat.format(calender.getTime());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Intent gotoGenerateQR = new Intent(this, GenerateQRCode.class);

                startActivity(gotoGenerateQR);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void JumpToReservationPage(View view) {

        Intent goToReservation = new Intent(this, Reservation.class);
        goToReservation.putExtra("userid", userid);
        startActivity(goToReservation);
    }

    public void JumpToViewReservation(View view) {
        if (rid == null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            alertDialog.setTitle("Notice!");
            alertDialog.setMessage("Please reserve your parking spot");
            AlertDialog alertDialogB = alertDialog.create();
            alertDialogB.show();
        }if(date.equals(currentdate)){
            BackgroundQRCode backgroundWorker = new BackgroundQRCode(this);
            backgroundWorker.execute(rid,time,parkingspot,userid);
        }
        else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            alertDialog.setTitle("Notice!");
            alertDialog.setMessage("Your reservation on "+date);
            AlertDialog alertDialogB = alertDialog.create();
            alertDialogB.show();

        }
    }

    public void JumpToUpdateProfile(View view) {
        BackgroundSelectProfile backgroundWorker = new BackgroundSelectProfile(this);
        backgroundWorker.execute(userid);
    }
    public void JumptoCheckOut(View view) {

        Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    calender = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    timeout = simpleDateFormat.format(calender.getTime());
                    Intent goToCheckOut = new Intent(Homepage.this, Checkout.class);
                    goToCheckOut.putExtra("timeout", timeout);
                    goToCheckOut.putExtra("rid", rid);
                    goToCheckOut.putExtra("time", time);
                    goToCheckOut.putExtra("checkin", checkin);
                    goToCheckOut.putExtra("parkingspot", parkingspot);
                    goToCheckOut.putExtra("userid", userid);
                    startActivity(goToCheckOut);

            }
        });
    }
}
