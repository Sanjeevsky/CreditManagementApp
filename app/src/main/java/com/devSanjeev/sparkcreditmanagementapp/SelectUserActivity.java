package com.devSanjeev.sparkcreditmanagementapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SelectUserActivity extends AppCompatActivity {

    private RecyclerView rvSelectUser;
    private DataBaseHelper mydb;
    int idr,id;
    double amount,credit;
    String name,email;
    ArrayList<UserAccountDetails> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        rvSelectUser = findViewById(R.id.recyclerSelectUser_id);
        rvSelectUser.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectUserActivity.this);
        rvSelectUser.setLayoutManager(layoutManager);
        mydb = new DataBaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        idr = bundle.getInt("id");
        amount = bundle.getDouble("amount");
        //Toast.makeText(SelectUserActivity.this,"Details : "+idr+" amount : "+amount,Toast.LENGTH_LONG).show();
        viewAll();
    }

    public void viewAll()
    {
        Cursor cursor = mydb.getAllData();
        if (cursor.getCount()>0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    id = cursor.getInt(0);
                    if(id != idr)
                    {
                        name = cursor.getString(1);
                        email = cursor.getString(2);
                        credit = cursor.getDouble(3);

                        UserAccountDetails userAccountDetails = new UserAccountDetails(id,name,email,credit,2,amount,idr);
                        arrayList.add(userAccountDetails);
                    }
                }while (cursor.moveToNext());
            }
            RecAdapter recAdapter = new RecAdapter(arrayList,this);
            rvSelectUser.setAdapter(recAdapter);
        }
    }
}
