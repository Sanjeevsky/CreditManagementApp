package com.devSanjeev.sparkcreditmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<UserAccountDetails> list;
    Context context;
    DataBaseHelper mydb;

    public RecAdapter(List<UserAccountDetails> users, Context mCtx) {
        this.list = users;
        this.context = mCtx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
        ViewHolder userviewHolder = new ViewHolder(view);
        return userviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final UserAccountDetails userAccountDetails = list.get(i);
        viewHolder.name.setText(userAccountDetails.name);
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userAccountDetails.flag == 1)
                {
                    userDetailsMethod(userAccountDetails);
                }
                else if(userAccountDetails.flag == 2)
                {
                    amountUpdate(userAccountDetails);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void userDetailsMethod(UserAccountDetails userAccountDetails){
        Intent intent = new Intent(context,UserAccountDetailActivity.class);
        intent.putExtra("id", userAccountDetails.id);
        context.startActivity(intent);
    }

    public void amountUpdate(UserAccountDetails userAccountDetails){
        mydb = new DataBaseHelper(context);
        Cursor cursor = mydb.getAllData();
        int id,count=0;
        double money;
        if (cursor.getCount()>0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    id = cursor.getInt(0);
                    if(id == userAccountDetails.idr)
                    {
                        count++;
                        money = cursor.getDouble(3) - userAccountDetails.credit;
                        mydb.updateData(id,cursor.getString(1),cursor.getInt(2),money);
                    }
                    if(id == userAccountDetails.id)
                    {
                        count++;
                        money = cursor.getDouble(3) + userAccountDetails.credit;
                        mydb.updateData(id,cursor.getString(1),cursor.getInt(2),money);
                    }
                }while (cursor.moveToNext());
            }

        }
        if (count == 2){
            boolean check = mydb.insertTransaction(userAccountDetails.id, userAccountDetails.idr, userAccountDetails.credit);
            if (check)
            {
                Toast.makeText(context,"Transaction Successful",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"Transaction Failed",Toast.LENGTH_LONG).show();
            }
        }
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent );
    }
}
