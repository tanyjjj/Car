package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectParkingSpace extends AppCompatActivity {
    Button carparkL101,carparkL102,carparkL103;
    String parking101,parking102;
    int status = 0;
    String time,userid;
    TextView L101,L102,L103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking_space);
        userid = getIntent().getExtras().getString("userid");
        time = getIntent().getExtras().getString("time");
        parking101 = getIntent().getExtras().getString("parking101");
        parking102 = getIntent().getExtras().getString("parking102");

        TextView Testing = (TextView) findViewById(R.id.trya);
          Testing.setText(parking101+parking102);


        final Button firstButton = (Button) findViewById(R.id.levelBtn1);
        Button secondButton = (Button) findViewById(R.id.levelBtn2);


        L101 = (TextView)findViewById(R.id.trya);

        carparkL101 = (Button)findViewById(R.id.carpark101);

        carparkL101.setEnabled(true);
        carparkL101.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String TEXT1 ="L1-01";
                   BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                    Worker.execute(TEXT1,userid,time);
                    Toast.makeText(getApplicationContext(), "L1-01 has been reserved.." ,  Toast.LENGTH_SHORT).show();
                    carparkL101.setEnabled(false);


            }
            });
        L102 = (TextView)findViewById(R.id.S102);
        carparkL102 = (Button)findViewById(R.id.carpark102);
        carparkL102.setEnabled(true);
        carparkL102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TEXT2 ="L1-02";
                BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                Worker.execute(TEXT2,userid,time);
                Toast.makeText(getApplicationContext(), "L1-02 has been reserved.." ,  Toast.LENGTH_SHORT).show();
                carparkL101.setEnabled(false);
            }
        });

        L103 = (TextView)findViewById(R.id.S103);
        carparkL103 = (Button)findViewById(R.id.carpark103);
        carparkL103.setEnabled(true);
        carparkL103.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TEXT3 ="L1-03";
                BackgroundParkingSpot Worker = new BackgroundParkingSpot(SelectParkingSpace.this);
                Worker.execute(TEXT3,userid,time);
                Toast.makeText(getApplicationContext(), "L1-03 has been reserved.." ,  Toast.LENGTH_SHORT).show();
                carparkL101.setEnabled(false);
            }
        });
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Clicked Level 1 Car Park");
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("Clicked Level 2 Car Park");

            }
        });

    }


    private void toastMessage(String message){
        Toast.makeText(SelectParkingSpace.this, message, Toast.LENGTH_SHORT).show();
    }
}
