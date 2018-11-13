package com.example.yongjiatan.car;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.android.gms.wallet.PaymentMethodToken;

public class PaymentPage extends AppCompatActivity {
    String rid,parkingspot,userid,totalParkingFee2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        rid = getIntent().getExtras().getString("rid");
        parkingspot = getIntent().getExtras().getString("parkingspot");
        userid = getIntent().getExtras().getString("userid");
       totalParkingFee2 = getIntent().getExtras().getString("totalParkingFee2");

        CardForm cardForm=(CardForm) findViewById(R.id.cardform);
        TextView amount = (TextView)findViewById(R.id.payment_amount);
        Button pay = (Button) findViewById(R.id.btn_pay);

        amount.setText(totalParkingFee2);
        pay.setText(String.format("Confirm",amount.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(PaymentPage.this, "Have a nice day", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentPage.this, Homepage.class);
                intent.putExtra("rid", rid);
                intent.putExtra("parkingspot", parkingspot);
                intent.putExtra("userid", userid);
               startActivity(intent);
            }
        });
    }
}
