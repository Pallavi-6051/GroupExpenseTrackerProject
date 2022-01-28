package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Add_Expenses extends AppCompatActivity {

    DataBaseHelper myDatabase;
    Spinner spinner;
    Button addExpense;
    TextView expenseType,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        myDatabase=new DataBaseHelper(this);
        addExpense=findViewById(R.id.addExpense);
        spinner=findViewById(R.id.spinner);
        expenseType=findViewById(R.id.expenseType);
        value=findViewById(R.id.value);

        ArrayList<String> nameList = initializeSpinner();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinner_layout,R.id.txt,nameList);
        spinner.setAdapter(adapter);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memSelected=spinner.getSelectedItem().toString();
                String ExpenseType = expenseType.getText().toString();
                String Value=value.getText().toString();

                if(memSelected.length()==0 || ExpenseType.length()==0 || Value.length()==0){
                    Toast.makeText(Add_Expenses.this, "Please fill valid Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean isInserted=myDatabase.insertDataTb2(ExpenseType,Value,memSelected.charAt(0)+"");
                if(isInserted)
                {
                    Toast.makeText(Add_Expenses.this, "Expense Added Successfully!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Add_Expenses.this, "Expense Not Added", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }


    public ArrayList<String> initializeSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDatabase.getAllData();
            if(res.getCount()==0){
                return list;
            }else{
                while(res.moveToNext()){
                    String memId = res.getString(0);
                    String memName = res.getString(1);
                    list.add(memId+"."+memName);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }


}