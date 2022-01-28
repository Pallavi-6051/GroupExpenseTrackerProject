package com.example.pallavi.practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ModifyExpenses extends AppCompatActivity {

    DataBaseHelper myDatabase;
    Spinner spMem, spExp;
    Button updateBtn;
    TextView expValue,warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_expenses);
        myDatabase = new DataBaseHelper(this);
        ArrayList<String> nameList = initializeMemSpinner();
        spMem = findViewById(R.id.sp_mem);
        spExp = findViewById(R.id.sp_exp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,nameList);
        spMem.setAdapter(adapter);
        updateBtn = findViewById(R.id.updateBtn);
        warningText = findViewById(R.id.warningText);
        spMem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = spMem.getSelectedItem().toString();
                ArrayList<String> ExpenseList = initializeExpSpinner(memSelected.charAt(0)+"");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModifyExpenses.this,R.layout.spinner_layout,R.id.txt,ExpenseList);
                spExp.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> ExpenseList = initializeExpSpinner("1");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModifyExpenses.this,R.layout.spinner_layout,R.id.txt,ExpenseList);
                spExp.setAdapter(adapter);
            }
        });
        spExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = spMem.getSelectedItem().toString();
                String expSelected = spExp.getSelectedItem().toString();
                Cursor cursor = myDatabase.getValueByExpenseNameAndId(expSelected,memSelected.charAt(0)+"");
                while(cursor.moveToNext()){
                    String Value = cursor.getString(2);
                    expValue = findViewById(R.id.expValue);
                    expValue.setHint("Current Value: Rs "+Value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int items = spExp.getAdapter().getCount();
                expValue = findViewById(R.id.expValue);
                String value = expValue.getText().toString();
                if(items==0){
                    warningText.setText("You cannot modify this data");
                    return;
                }else{
                    if(value.length()==0){
                        warningText.setText("Please enter a valid value");
                        return;
                    }else{
                        warningText.setText("");
                        String memSelected = spMem.getSelectedItem().toString();
                        String expSelected = spExp.getSelectedItem().toString();
                        boolean isUpdated = myDatabase.updateByIdAndExpense(expSelected,memSelected.charAt(0)+"",value);
                        if(isUpdated)
                            Toast.makeText(ModifyExpenses.this,"Data Modified",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ModifyExpenses.this,"Data Not Modified",Toast.LENGTH_LONG).show();
                        expValue.setText("");
                        expValue.setHint("Current Value: Rs "+value);
                    }
                }
            }
        });
    }
    public ArrayList<String> initializeMemSpinner(){
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

    public ArrayList<String> initializeExpSpinner(String Id){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDatabase.getExpenseById(Id);
            if(res.getCount()==0){
                expValue = findViewById(R.id.expValue);
                expValue.setHint("Currently having no expense");
                return list;
            }else{
                while(res.moveToNext()){
                    String Expense = res.getString(1);
                    list.add(Expense);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }

}