package com.example.yongjiatan.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectParkingSpace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parking_space);

        Button firstButton = (Button) findViewById(R.id.levelBtn1);
        Button secondButton = (Button) findViewById(R.id.levelBtn2);

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
