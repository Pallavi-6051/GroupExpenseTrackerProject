package com.example.pallavi.practice_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class TripDetail extends AppCompatActivity {

    TextView tripName,totalExpense;
    ListView memberList;
    Button endTrip;
    Cursor cursor;
    DataBaseHelper myDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        myDatabase=new DataBaseHelper(this);
        cursor=myDatabase.getAllData();
        endTrip=findViewById(R.id.endTrip);
        tripName=findViewById(R.id.tripName);
        totalExpense=findViewById(R.id.totalExpense);
        memberList=findViewById(R.id.memberList);

        cursor.moveToFirst();
        String tripNameExtracted=cursor.getString(2);
        tripName.setText(tripNameExtracted);

        ArrayList<String> list=new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            do{
                String memberId=cursor.getString(0);
                String memberName=cursor.getString(1);
                list.add(memberId+". "+memberName);
            }while(cursor.moveToNext());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        memberList.setAdapter(arrayAdapter);
        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String member=memberList.getItemAtPosition(i).toString();
                Toast.makeText(TripDetail.this,""+member,Toast.LENGTH_SHORT).show();
            }
        });

        cursor=myDatabase.getAllExpenses();
        int sum=0;
        Integer  value;
        if(cursor.moveToFirst())
        {
            do{
                value=Integer.parseInt(cursor.getString(2));
                sum+=value;
            }while(cursor.moveToNext());
        }

        totalExpense.setText("Total Trip Expense: Rs. "+sum);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(TripDetail.this);
                builder.setMessage("Are you sure you want to finish this Trip and close the app?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean isDropped=myDatabase.dropDatabase();
                                if(isDropped)
                                {
                                    Toast.makeText(TripDetail.this,"Trip Ended Successfully!!",Toast.LENGTH_SHORT);
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("EXIT",true);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(TripDetail.this,"Error in Ending Trip",Toast.LENGTH_SHORT);
                                    dialogInterface.cancel();

                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert= builder.create();
                alert.setTitle("Alert!!");
                alert.show();

            }
        });

    }
}