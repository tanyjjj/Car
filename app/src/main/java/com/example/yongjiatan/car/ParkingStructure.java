package com.example.yongjiatan.car;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ParkingStructure extends AppCompatActivity {
    Button submit;
    Spinner spinner,timespinner;
    String storedate ;
    String userid,time;

    private TextView mDisplayDate;
    private static final String TAG = "ParkingStructure";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkingstructure);

        submit = (Button)findViewById(R.id.submit_btn);
        userid = getIntent().getExtras().getString("userid");

        spinner = (Spinner) findViewById(R.id.parkingspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.parking_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);




        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ParkingStructure.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                if(month <10){
                    storedate = year + "-0" + month + "-" + day;
                } else {
                    storedate = year + "-" + month + "-" + day;
                }


             //   Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "-" + day + "-" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);

            }
        };

        timespinner = (Spinner) findViewById(R.id.spinnertime);

        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this,R.array.time, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timespinner.setAdapter(Adapter);



     submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             AlertDialog.Builder alert = new AlertDialog.Builder(ParkingStructure.this);



             Boolean flag = true;

             if (spinner.getSelectedItem().toString().equals("Select Location")) {
                 flag = false;
                 alert.setTitle("Make Reservation");
                 alert.setMessage("Please select Parking Location!!");

             }
             if (timespinner.getSelectedItem().toString().equals("Select Time")) {
                 flag = false;
                 alert.setTitle("Make Reservation");
                 alert.setMessage("Please select Time!!");
             }

             if (storedate == (null)) {
                 flag = false;
                 alert.setTitle("Make Reservation");
                 alert.setMessage("Please selectDate!!");
             }
             if (spinner.getSelectedItem().toString().equals("Select Location")&&(timespinner.getSelectedItem().toString().equals("Select Time")&&(storedate == (null)))){
                 alert.setTitle("Make Reservation");
                 alert.setMessage("Please select Reservation details!!");
             }
             if (flag.equals(false)) {
                 AlertDialog dialog = alert.create();
                 dialog.show();
             }else{
                 String text = spinner.getSelectedItem().toString();
                 time = timespinner.getSelectedItem().toString();
                 BackgroundWorkerParkingStructure backgroundWorker = new BackgroundWorkerParkingStructure(ParkingStructure.this);
                 backgroundWorker.execute(text, time, storedate, userid);
             }
         }
     });

    }
}

