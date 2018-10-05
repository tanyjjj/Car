package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Login extends AppCompatActivity {
    EditText useridLogin,passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        useridLogin = (EditText) findViewById(R.id.login_userid);
        passwordLogin = (EditText) findViewById(R.id.Epassword);
    }

    public void OnLogin(View view){
        String useridlogin = useridLogin.getText().toString();
        String passwordlogin = passwordLogin.getText().toString();
        String type ="login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,useridlogin,passwordlogin);

    }

    public void JumptoRegister(View view)
    {
        Intent gotoRegister = new Intent(this,RegistrationPage.class);
        startActivity(gotoRegister);
    }
}