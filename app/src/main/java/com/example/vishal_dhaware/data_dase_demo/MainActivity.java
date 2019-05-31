package com.example.vishal_dhaware.data_dase_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button add,delet,modify,viwe,viewAll,about;
    EditText mark,roll;
    EditText name;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OparectoinOnfindview();
        OparectionObject();
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");
    }

    private void OparectionObject() {
        add.setOnClickListener(this);
        delet.setOnClickListener(this);
        modify.setOnClickListener(this);
        viwe.setOnClickListener(this);
        viewAll.setOnClickListener(this);
        about.setOnClickListener(this);
       // db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
    }

    private void OparectoinOnfindview() {
        add=findViewById(R.id.Add);
        delet=findViewById(R.id.Delete);
        modify=findViewById(R.id.Modify);
        viwe=findViewById(R.id.View);
        viewAll=findViewById(R.id.Viwe_All);
        about=findViewById(R.id.About);
        name=findViewById(R.id.edit_name);
        mark=findViewById(R.id.edit_mark);
        roll=findViewById(R.id.edit_Roll);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Add:
                if(view==add) {
                    if (roll.getText().toString().trim().length() == 0 ||
                            name.getText().toString().trim().length() == 0 ||
                            mark.getText().toString().trim().length() == 0) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                        showMessage("Error", "Please enter all values");
                        return;
                    }
                    db.execSQL("INSERT INTO student VALUES('" + roll.getText() + "','" + name.getText() +
                            "','" + mark.getText() + "');");
                    showMessage("Success", "Record added");
                    clearText();
                }
                Toast.makeText(MainActivity.this,"Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Delete:
                if(roll.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+roll.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+roll.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                Toast.makeText(MainActivity.this,"Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Modify:
                if(roll.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor a=db.rawQuery("SELECT * FROM student WHERE rollno='"+roll.getText()+"'", null);
                if(a.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+name.getText()+"',marks='"+mark.getText()+
                            "' WHERE rollno='"+roll.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                Toast.makeText(MainActivity.this,"Modify",Toast.LENGTH_SHORT).show();
                break;
            case R.id.View:
                if(roll.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor b=db.rawQuery("SELECT * FROM student WHERE rollno='"+roll.getText()+"'", null);
                if(b.moveToFirst())
                {
                    name.setText(b.getString(1));
                    mark.setText(b.getString(2));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
                Toast.makeText(MainActivity.this,"View",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Viwe_All:
                Cursor d=db.rawQuery("SELECT * FROM student", null);
                if(d.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(d.moveToNext())
                {
                    buffer.append("Rollno: "+d.getString(0)+"\n");
                    buffer.append("Name: "+d.getString(1)+"\n");
                    buffer.append("Marks: "+d.getString(2)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
                Toast.makeText(MainActivity.this,"Viwe_All",Toast.LENGTH_SHORT).show();
                break;
            case R.id.About:
                Toast.makeText(MainActivity.this,"About",Toast.LENGTH_SHORT).show();
                showMessage("Seccesefully On Student record ","Develop By Me");
                break;

        }

    }

    private void clearText() {
        mark.setText("");
        roll.setText("");
        name.setText("");

    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
