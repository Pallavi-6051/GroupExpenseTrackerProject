package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddMembers extends AppCompatActivity {


    DataBaseHelper myDatabase;
    String tripName;
    String groupSize;
    int size;

    public void addMembers(int i) {
        final Button btn = findViewById(R.id.addMember);
        if (size == i) {
            btn.setText("Submit");
        }
        if (i > size) {
            return;
        }
        final int id = i;
        final TextView memberName = findViewById(R.id.memberName);
        TextView warningMessage = findViewById(R.id.warningText);
        memberName.setHint(id + ". Member Name");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memberName.getText().length() == 0) {
                    warningMessage.setText("Please enter a valid Member name!!");
                    return;
                }
                boolean isInserted = myDatabase.insertDataTb1(memberName.getText().toString(), tripName);
                if (isInserted) {
                    Toast.makeText(AddMembers.this, "Member Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddMembers.this, "Member Not Added", Toast.LENGTH_SHORT).show();

                }
                if (btn.getText().equals("Submit")) {
                    Intent i = new Intent(AddMembers.this, Home.class);
                    startActivity(i);
                }
                memberName.setText("");
                addMembers(id + 1);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        myDatabase=new DataBaseHelper(this);
        tripName=getIntent().getStringExtra("TripName");
        groupSize=getIntent().getStringExtra("GroupSize");
        size=Integer.parseInt(groupSize);
        addMembers(1);

    }
}