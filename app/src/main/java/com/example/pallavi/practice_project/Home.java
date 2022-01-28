package com.example.pallavi.practice_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Home extends AppCompatActivity {

    Button btnmenu,members,addExpense,modifyExpense,calculate,tripDetail,closeApp;
    RelativeLayout maincontent;
    LinearLayout mainmenu,mainHome;
    Animation fromTop,fromBottom,fromBottomSlow;
    ImageView devNameImg;
    TextView appName,tripDisplay,devName1,devName2;
    LottieAnimationView lottieAnimationView1,lottieAnimationView2;
    Cursor cursor;
    DataBaseHelper myDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDatabase=new DataBaseHelper(this);
        cursor=myDatabase.getAllData();
        tripDisplay=findViewById(R.id.tripDisplay);
        cursor.moveToFirst();
        String tripName=cursor.getString(2);
        tripDisplay.setText(tripName);

        btnmenu=findViewById(R.id.btnmenu);
        maincontent=findViewById(R.id.maincontent);
        mainmenu=findViewById(R.id.mainmenu);
        mainHome=findViewById(R.id.mainHome);
        lottieAnimationView1=findViewById(R.id.lottieAnimationView1);
        lottieAnimationView2=findViewById(R.id.lottieAnimationView2);

        members=findViewById(R.id.members);
        addExpense=findViewById(R.id.addExpense);
        modifyExpense=findViewById(R.id.modifyExpense);
        calculate=findViewById(R.id.calculate);
        tripDetail=findViewById(R.id.tripDetail);
        closeApp=findViewById(R.id.closeApp);


        appName  = findViewById(R.id.appName);

        devName1 = findViewById(R.id.devName1);
        devName2 = findViewById(R.id.devName2);
        devNameImg = findViewById(R.id.devNameimg);


        fromTop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromBottomSlow= AnimationUtils.loadAnimation(this,R.anim.frombottomslow);

        lottieAnimationView1.playAnimation();
        lottieAnimationView2.playAnimation();

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maincontent.animate().translationX(0);
                mainmenu.animate().translationX(0);

                members.startAnimation(fromBottom);
                addExpense.startAnimation(fromBottom);
                modifyExpense.startAnimation(fromBottom);
                calculate.startAnimation(fromBottom);
                tripDetail.startAnimation(fromBottom);
                closeApp.startAnimation(fromBottom);

                lottieAnimationView2.startAnimation(fromTop);
                appName.startAnimation(fromTop);

                devName1.startAnimation(fromBottomSlow);
                devName2.startAnimation(fromBottomSlow);
                devNameImg.startAnimation(fromBottomSlow);
            }
        });


        maincontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maincontent.animate().translationX(-mainmenu.getWidth());
                mainmenu.animate().translationX(-mainmenu.getWidth());
            }
        });
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Members.class);
                startActivity(intent);
            }
        });
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Add_Expenses.class);
                startActivity(intent);
            }
        });
        modifyExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ModifyExpenses.class);
                startActivity(intent);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Calculate.class);
                startActivity(intent);
            }
        });
        tripDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,TripDetail.class);
                startActivity(intent);
            }
        });
        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setMessage("Are you sure you want to close this app ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT",true);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Alert !!!");
                alertDialog.show();
            }
        });


    }
}