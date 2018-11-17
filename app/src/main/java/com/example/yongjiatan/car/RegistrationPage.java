package com.example.yongjiatan.car;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationPage extends AppCompatActivity {
    EditText userid, password, name, contactNo, dateOfBirth, email;
    CheckBox check;
Button reg;
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
        reg = (Button) findViewById(R.id.registrationsubmit);
        TextView txt = (TextView) findViewById(R.id.tc);
        txt.setPaintFlags(txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        check = (CheckBox)findViewById(R.id.checkBox1);
        if(!validateUserID(userid.getText().toString())) {
            userid.setError("UserID length must be minimum character 5 ");
            userid.requestFocus();
        }
        //password.getText().toString().length()<8 &&
        if(!validatePassword(password.getText().toString())||password.getText().toString().length()<8){
            password.setError("Password must be at least 8 characters,one number,one special character and upper case letter ");
            password.requestFocus();
        }
        if(!validateEmail(email.getText().toString())) {
            email.setError("Please enter valid email format!");
            email.requestFocus();
        }
        if(!validateName(name.getText().toString())) {
            name.setError("PLease enter your name");
            name.requestFocus();
        }
        if(!validateContact(contactNo.getText().toString())) {
            contactNo.setError("PLease enter your phone number");
            contactNo.requestFocus();
        }
        if(!validateDate(dateOfBirth.getText().toString())) {
            dateOfBirth.setError("PLease enter your date of birth");
            dateOfBirth.requestFocus();
        }


    }
    public void JumpToTC(View view) {

        Intent goToTC = new Intent(this, TermAndCondition.class);
        startActivity(goToTC);
    }
    public void itemClicked(View v) {
        if(check.isChecked()){
            reg.setEnabled(true);

        }else{
            reg.setEnabled(false);
        }
    }
   /** public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        // TODO Auto-generated method stub
        if(arg0.isChecked()){
           reg.setVisibility(View.VISIBLE);
        }else{
            reg.setVisibility(View.INVISIBLE);
        }
    } **/
    public void OnRegister(View view) {
        if(!validateUserID(userid.getText().toString())) {
            userid.setError("UserID length must be minimum character 5 ");
            userid.requestFocus();
        }
        //password.getText().toString().length()<8 &&
        if(!validatePassword(password.getText().toString())||password.getText().toString().length()<8){
            password.setError("Password must be at least 8 characters,one number,one special character and upper case letter ");
            password.requestFocus();
        }
        if(!validateEmail(email.getText().toString())) {
            email.setError("Email must have valid email format.Please try again!");
            email.requestFocus();
        }

        if ((!validatePassword(password.getText().toString()) || password.getText().toString().length() < 8)
                && (!validateEmail(email.getText().toString())) && (!validateUserID(userid.getText().toString()))&&(password.getText().toString()=="")&&(userid.getText().toString()=="")) {
            Toast.makeText(getApplicationContext(), "Please enter valid format", Toast.LENGTH_SHORT).show();
        }
        if(!validateName(name.getText().toString())) {
            name.setError("PLease enter your name");
           name.requestFocus();
        }
        if(!validateContact(contactNo.getText().toString())) {
            contactNo.setError("PLease enter valid phone number");
            contactNo.requestFocus();
        }
        if(!validateDate(dateOfBirth.getText().toString())) {
            dateOfBirth.setError("PLease enter your date of birth");
            dateOfBirth.requestFocus();
        }

        else {

            String RUserid = userid.getText().toString();
            String RPassword = password.getText().toString();
            String RName = name.getText().toString();
            String REmail = email.getText().toString();
            String RContactno = contactNo.getText().toString();
            String RDateofbirth = dateOfBirth.getText().toString();
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, RUserid, RPassword, RName, RContactno, RDateofbirth, REmail);
        }
    }
    protected boolean validatePassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    protected boolean validateUserID(String userid) {
        if(userid!=null && userid.length()>5) {
            return true;
        } else {
            return false;
        }

    }
    protected boolean validateName(String Name) {

        if(Name.length()==0){
            return false;

        }else {
            return true;
        }
    }
    protected boolean validateContact(String contactNo) {

        if( contactNo.length()!=10){
            return false;

        }else {
            return true;
        }
    }
    protected boolean validateDate(String dateOfBirth) {

        if (dateOfBirth.length() != 10) {
            return false;

        } else {
            return true;
        }
    }


}