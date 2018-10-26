package com.example.yongjiatan.car;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Homepage extends AppCompatActivity {
    String userid,rid,time,checkin,parkingspot;
    Calendar calender;
    SimpleDateFormat simpleDateFormat;
    String timeout;
    TextView Text;

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        rid = getIntent().getExtras().getString("rid");
        time = getIntent().getExtras().getString("time");
        userid = getIntent().getExtras().getString("userid");
        checkin = getIntent().getExtras().getString("checkin");
      parkingspot = getIntent().getExtras().getString("parkingspot");

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
        } else {
            Intent goToView = new Intent(this, GenerateQRCode.class);
            goToView.putExtra("userid", userid);
            goToView.putExtra("rid", rid);
            goToView.putExtra("time", time);
            startActivity(goToView);
        }
    }

    public void JumpToUpdateProfile(View view) {
        Intent goToUpdate = new Intent(this, EditProfile.class);
        goToUpdate.putExtra("userid", userid);
        startActivity(goToUpdate);
    }
    public void JumptoCheckOut(View view) {

        Text = (TextView) findViewById(R.id.checkoutText);
        Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (rid==null) {
                    AlertDialog.Builder alertDialog= new AlertDialog.Builder(
                            context);
                    alertDialog.setTitle("Notice!");
                    alertDialog.setMessage("Please reserve your parking spot");
                    AlertDialog alertDialogB = alertDialog.create();

                    alertDialogB.show();
                } else {
                   calender = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    timeout = simpleDateFormat.format(calender.getTime());
                    Intent intent = new Intent(Homepage.this, Checkout.class);
                    intent.putExtra("timeout", timeout);
                    intent.putExtra("rid", rid);
                    intent.putExtra("time", time);
                    intent.putExtra("checkin", checkin);
                    intent.putExtra("parkingspot", parkingspot);
                    startActivity(intent);
              }
            }
        });
    }
}

