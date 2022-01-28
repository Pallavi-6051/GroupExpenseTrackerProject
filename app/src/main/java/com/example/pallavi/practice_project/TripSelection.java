package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TripSelection extends AppCompatActivity {

    private TextView tripNameTextView;
    private String TripName;
    DataBaseHelper myDatabase;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_selection);


        myDatabase=new DataBaseHelper(this);
        cursor=myDatabase.getAllData();
        if(cursor.moveToFirst())
        {
            String TripName=cursor.getString(2);
            Intent i=new Intent(TripSelection.this,Home.class);
            startActivity(i);
            return;
        }

        final Button addTripBtn= findViewById(R.id.addTrip);
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripNameTextView=findViewById(R.id.tripName);
                TextView warningMessage=findViewById(R.id.warningText);
                if(tripNameTextView.getText().length()==0)
                {
                    warningMessage.setText("Please Enter a Valid Trip Name!!");
                    Toast.makeText(TripSelection.this,"Oops...",Toast.LENGTH_SHORT);
                }
                else{
                    warningMessage.setText("");
                    TripName=tripNameTextView.getText().toString();
                    Toast.makeText(TripSelection.this,"Trip Selected",Toast.LENGTH_SHORT);
                    Intent i=new Intent(TripSelection.this,GroupCreation.class);
                    i.putExtra("TripName",TripName);
                    startActivity(i);

                }
            }
        });



    }
}