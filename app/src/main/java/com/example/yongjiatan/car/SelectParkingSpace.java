package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectParkingSpace extends AppCompatActivity {
    Button carparkL101,carparkL102,carparkL103;
    String parking101,parking102;

    String time,userid,date;

    String TEXT1,TEXT2,TEXT3;
    ArrayList<String> resultArray;
    String[] carpark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking_space);
        userid = getIntent().getExtras().getString("userid");
        time = getIntent().getExtras().getString("time");
        date = getIntent().getExtras().getString("date");
    resultArray=  getIntent().getStringArrayListExtra("parking");


        carpark = new String[resultArray.size()];
        final Button firstButton = (Button) findViewById(R.id.levelBtn1);
        Button secondButton = (Button) findViewById(R.id.levelBtn2);
        firstButton.setEnabled(false);

        carparkL101 = (Button)findViewById(R.id.carpark101);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if(carpark[k].contains("L1-01")){

                carparkL101.setEnabled(false);
                carparkL101.setBackgroundColor(Color.RED);
            } else{
                carparkL101.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TEXT1 = "L1-01";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                        Worker.execute(TEXT1, userid, time,date);
                        Toast.makeText(getApplicationContext(), "L1-01 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL101.setEnabled(false);
                    }
                });
            }
        }

        carparkL102 = (Button)findViewById(R.id.carpark102);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if (carpark[k].contains("L1-02")) {
                carparkL102.setEnabled(false);
                carparkL102.setBackgroundColor(Color.RED);
            } else {
                carparkL102.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TEXT2 = "L1-02";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                        Worker.execute(TEXT2, userid, time,date);
                        Toast.makeText(getApplicationContext(), "L1-02 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL102.setEnabled(false);
                    }
                });
            }
        }

        carparkL103 = (Button)findViewById(R.id.carpark103);
        for (int k = 0; k < resultArray.size();k++) {
            carpark[k] = resultArray.get(k);
            if (carpark[k].contains("L1-03")) {
                carparkL103.setEnabled(false);
                carparkL103.setBackgroundColor(Color.RED);
            } else {
                carparkL103.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TEXT3 = "L1-03";
                        BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                        Worker.execute(TEXT3, userid, time,date);
                        Toast.makeText(getApplicationContext(), "L1-03 has been reserved..", Toast.LENGTH_SHORT).show();
                        carparkL103.setEnabled(false);
                    }
                });
            }
        }



        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Clicked Level 2 Car Park");
             Intent intent = new Intent(getApplicationContext(),SelectParkingSpaceSecond.class);
           intent.putExtra("time", time);
             intent.putExtra("userid",userid);
                intent.putExtra("date",date);
            intent.putStringArrayListExtra("parking",resultArray);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(SelectParkingSpace.this, message, Toast.LENGTH_SHORT).show();
    }
}
