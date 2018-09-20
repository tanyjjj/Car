package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        userid = getIntent().getExtras().getString("userid");
    }

    public void JumpToReservationPage(View view) {
        Intent goToReservation = new Intent(this, Reservation.class);
        goToReservation.putExtra("userid",userid);
        startActivity(goToReservation);
    }
    public void JumptoScanQRCode(View view)
    {
        Intent gotoScanQR = new Intent(this,ScanQRCode.class);
        startActivity(gotoScanQR);
    }
}
