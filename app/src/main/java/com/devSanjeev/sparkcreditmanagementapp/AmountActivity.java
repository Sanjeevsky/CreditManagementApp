package com.devSanjeev.sparkcreditmanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AmountActivity extends AppCompatActivity {

    int idr;
    double amount,credit;
    DataBaseHelper mydb;
    private EditText etxtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        etxtAmount = findViewById(R.id.etxt_amount_id);
        Button btnContinue = findViewById(R.id.btn_continue_id);
        Bundle bundle = getIntent().getExtras();
        idr = bundle.getInt("id");
        amount = bundle.getDouble("amount");


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check()) {
                    Intent intent = new Intent(AmountActivity.this, SelectUserActivity.class);
                    intent.putExtra("id", idr);
                    intent.putExtra("amount", credit);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean check(){
        Double debitAmount=Double.parseDouble(etxtAmount.getText().toString());


        if(!(debitAmount <=0.0)){
            credit = Double.parseDouble(etxtAmount.getText().toString());
            if (amount >= credit) {
                return true;
            } else {
                etxtAmount.setError("Change Amount");
                Toast.makeText(AmountActivity.this, "Entered Amount Is Greater Than Maximum Available Amount", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            Toast.makeText(AmountActivity.this, "Enter Amount Greater Than 0.0", Toast.LENGTH_LONG).show();
            return false;

        }
    }
}
