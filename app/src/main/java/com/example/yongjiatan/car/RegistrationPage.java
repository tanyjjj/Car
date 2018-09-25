package com.example.yongjiatan.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationPage extends AppCompatActivity {
    EditText userid, password, name, contactNo, dateOfBirth, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        userid = (EditText) findViewById(R.id.Euserrid);
        name = (EditText) findViewById(R.id.Ename);
        password = (EditText) findViewById(R.id.Epassword);
        email = (EditText) findViewById(R.id.Eemail);
        contactNo = (EditText) findViewById(R.id.Econtactno);
        dateOfBirth = (EditText) findViewById(R.id.Edate);
    }
    public void OnRegister(View view) {
        String  RUserid = userid.getText().toString();
        String RPassword = password.getText().toString();
        String RName = name.getText().toString();
        String REmail = email.getText().toString();
        String RContactno = contactNo.getText().toString();
        String RDateofbirth = dateOfBirth.getText().toString();
        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, RUserid, RPassword, RName, REmail, RContactno, RDateofbirth);
    }
}