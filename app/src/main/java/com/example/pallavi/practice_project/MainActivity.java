package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button loadingMe;
    ImageView mainLogo;
    Animation rotate,scale;
    TextView appName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getBooleanExtra("EXIT",false)){
            finish();
        }

        appName=findViewById(R.id.appName);
        mainLogo=findViewById(R.id.logo);

        scale= AnimationUtils.loadAnimation(this,R.anim.scale);
        rotate= AnimationUtils.loadAnimation(this,R.anim.rotate);

        appName.startAnimation(scale);
        mainLogo.startAnimation(rotate);

       loadingMe=findViewById(R.id.start);
       loadingMe.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity.this, TripSelection.class);
               startActivity(i);
           }
       });





    }
}