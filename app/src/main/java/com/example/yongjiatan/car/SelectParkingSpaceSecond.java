package com.example.yongjiatan.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectParkingSpaceSecond  extends AppCompatActivity {

    Button carparkL201,carparkL202,carparkL203;
    String time,userid;
    TextView L201,L202,L203;
    String TEXT1,TEXT2,TEXT3;
    ArrayList<String> resultArray;
    String[] carpark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking_space_second);
        userid = getIntent().getExtras().getString("userid");
        time = getIntent().getExtras().getString("time");
        resultArray=  getIntent().getStringArrayListExtra("parking");

       carpark = new String[resultArray.size()];
        final Button firstButton = (Button) findViewById(R.id.levelBtn1);
        final Button secondButton = (Button) findViewById(R.id.levelBtn2);
        secondButton.setEnabled(false);

        L201 = (TextView)findViewById(R.id.W101);
        carparkL201 = (Button)findViewById(R.id.carpark201);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if(carpark[k].contains("L2-01")){
                carparkL201.setEnabled(false);
            } else{
                carparkL201.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TEXT1 = "L2-01";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpaceSecond.this);
                        Worker.execute(TEXT1, userid, time);
                        Toast.makeText(getApplicationContext(), "L2-01 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL201.setEnabled(false);
                    }
                });
            }
        }


        L202 = (TextView)findViewById(R.id.W102);
        carparkL202 = (Button)findViewById(R.id.carpark202);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if (carpark[k].contains("L2-02")) {
                carparkL202.setEnabled(false);
            } else {
                carparkL202.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TEXT2 = "L2-02";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpaceSecond.this);
                        Worker.execute(TEXT2, userid, time);
                        Toast.makeText(getApplicationContext(), "L2-02 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL202.setEnabled(false);
                    }
                });
            }
        }
        L203 = (TextView)findViewById(R.id.W103);
        carparkL203 = (Button)findViewById(R.id.carpark203);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if (carpark[k].contains("L2-03")) {
                carparkL203.setEnabled(false);
            } else {
                carparkL203.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TEXT3 = "L2-03";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpaceSecond.this);
                        Worker.execute(TEXT3, userid, time);
                        Toast.makeText(getApplicationContext(), "L2-03 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL203.setEnabled(false);
                    }
                });
            }
        }
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Clicked Level 1 Car Park");
                Intent goToLvl1 = new Intent(SelectParkingSpaceSecond.this, SelectParkingSpace.class);
               goToLvl1.putExtra("time", time);
                goToLvl1.putExtra("userid",userid);
               goToLvl1.putStringArrayListExtra("parking",resultArray);
                startActivity(goToLvl1);
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(SelectParkingSpaceSecond.this, message, Toast.LENGTH_SHORT).show();
    }
}

