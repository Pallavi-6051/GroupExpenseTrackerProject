package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupCreation extends AppCompatActivity {

    String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);

        tripName=getIntent().getStringExtra("TripName");
        TextView tripNameHeader=findViewById(R.id.tripNameHeader);
        tripNameHeader.setText("Trip: "+tripName);

        Button sizeSelectionBtn=findViewById(R.id.sizeSelectionBtn);
        sizeSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView group=findViewById(R.id.groupSize);
                TextView warningMessage=findViewById(R.id.warningText);
                try {
                    Integer groupSize = Integer.parseInt(group.getText().toString());
                    if (groupSize <= 1) {
                        warningMessage.setText("Please enter a valid Group Size!!");
                    } else {
                        warningMessage.setText("");
                        Intent i = new Intent(GroupCreation.this, AddMembers.class);
                        i.putExtra("TripName", tripName);
                        i.putExtra("GroupSize", groupSize.toString());
                        startActivity(i);
                    }
                }
                catch(Exception e)
                {
                    warningMessage.setText("Please enter a valid Group Size!!");
                }
            }
        });




    }
}