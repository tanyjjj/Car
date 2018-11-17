package com.example.yongjiatan.car;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.TextWatcher;

public class Login extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener {
    EditText useridLogin,passwordLogin;
    private CheckBox remember_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        useridLogin = (EditText) findViewById(R.id.login_userid);
        passwordLogin = (EditText) findViewById(R.id.Epassword);
        remember_userpass = (CheckBox)findViewById(R.id.checkBox);


        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            remember_userpass.setChecked(true);
        else
            remember_userpass.setChecked(false);

        useridLogin.setText(sharedPreferences.getString(KEY_USERNAME,""));
        passwordLogin.setText(sharedPreferences.getString(KEY_PASS,""));

        useridLogin.addTextChangedListener(this);
        passwordLogin.addTextChangedListener(this);
        remember_userpass.setOnCheckedChangeListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(remember_userpass.isChecked()){
            editor.putString(KEY_USERNAME, useridLogin.getText().toString().trim());
            editor.putString(KEY_PASS, passwordLogin.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
    public void OnLogin(View view){
//Admin@FYP2  Admin1997**Aug
        if(useridLogin.getText().toString().equals("a")&&passwordLogin.getText().toString().equals("b")){
            Intent gotoAdminHomepage = new Intent(this,AdminHomepage.class);
            startActivity(gotoAdminHomepage);
        } else {
            String useridlogin = useridLogin.getText().toString();
            String passwordlogin = passwordLogin.getText().toString();
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, useridlogin, passwordlogin);
        }
    }

    public void JumptoRegister(View view)
    {
        Intent gotoRegister = new Intent(this,RegistrationPage.class);
        startActivity(gotoRegister);
    }
}