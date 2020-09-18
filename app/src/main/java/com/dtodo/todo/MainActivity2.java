package com.dtodo.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper myDB;
    List<TodoList> list;
    ListView listView;
    MyAdapter myAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.apple);
        myDB = new DatabaseHelper(this);
        updateListView();


        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(MainActivity2.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("Add a new Task")
                        .setMessage("")
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                editText.requestFocus();
//                                editText.setFocusableInTouchMode(true);

                                String task = editText.getText().toString();
                                if(task.equals(String.valueOf(""))){
                                    Toast.makeText(MainActivity2.this,"Opps!! no task entered",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                boolean isInserted = myDB.addData(task);
                                if(isInserted == true){
                                    Toast.makeText(MainActivity2.this,"Todo inserted",Toast.LENGTH_SHORT).show();
                                    updateListView();
                                }
                                else {
                                    Toast.makeText(MainActivity2.this,"went wrong",Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
            }

        });


    }


    public void updateListView(){
        listView.setAdapter(null);
        list =new ArrayList<>();

        Cursor cursor= myDB.readData();
        while (cursor.moveToNext()){
            list.add(new TodoList(cursor.getString(1)));
        }
        myAdapter= new MyAdapter(this,R.layout.list_item_background,list);
        listView.setAdapter(myAdapter);
    }

    public void deleteTask(final View view){

        AlertDialog builder = new AlertDialog.Builder(MainActivity2.this)
                .setTitle("Are you sure you want to Delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View parent = (View) view.getParent();
                        TextView textView = parent.findViewById(R.id.textView);
                        String task =textView.getText().toString();
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COL_2+"=?", new String[]{task});
                        db.close();
                        updateListView();
                    }
                })
                .setNegativeButton("No",null)
                .create();
        builder.show();


//        View parent = (View) view.getParent();
//        TextView textView = parent.findViewById(R.id.textView);
//        String task =textView.getText().toString();
//        SQLiteDatabase db = myDB.getWritableDatabase();
//        db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.COL_2+"=?", new String[]{task});
//        db.close();
//        updateListView();
    }

}