package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectParkingSpace extends AppCompatActivity {
    Button carpark1,carpark2;
    int count = 0;
    String rid,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking_space);

     rid = getIntent().getExtras().getString("rid");
        time = getIntent().getExtras().getString("time");

        final Button firstButton = (Button) findViewById(R.id.levelBtn1);
        Button secondButton = (Button) findViewById(R.id.levelBtn2);
        Button thirdButton = (Button) findViewById(R.id.home_Btn);
        carpark1 = (Button)findViewById(R.id.carpark101);
        carpark2 = (Button)findViewById(R.id.carpark102);
            carpark1.setEnabled(true);
            carpark1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count == 0) {
                    toastMessage("no");
                    carpark1.setEnabled(false);
                } else{
                        carpark1.setEnabled(true);
                    }
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
       thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SelectParkingSpace.this,Homepage.class);
                intent.putExtra("rid", rid);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(SelectParkingSpace.this, message, Toast.LENGTH_SHORT).show();
    }
}
