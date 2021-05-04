package com.e.system_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class View_db_Activity extends AppCompatActivity {
    private ListView lst1;
    ArrayList<String> title = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_db_);
        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        final Cursor c = db.rawQuery("select * from records",null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("time");
        int course = c.getColumnIndex("msg");
        int fee = c.getColumnIndex("date");
        title.clear();


        arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,title);
        lst1.setAdapter(arrayAdapter);

        final  ArrayList<student> stud = new ArrayList<student>();
        if (c.moveToFirst()){
            do {
                student stu = new student();
                stu.time = c.getString(name);
                stu.msg = c.getString(course);
                stu.date = c.getString(fee);
                stud.add(stu);

                title.add(c.getString(name)+"\t"+c.getString(course)+"\t"+c.getString(fee));

            }while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();
        }
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String aa = title.get(position).toString();
                student stu = stud.get(position);
                Intent i = new Intent(getApplicationContext(),DisplayActivity.class);
                i.putExtra("name",stu.time);
                i.putExtra("course",stu.msg);
                i.putExtra("fee",stu.date);
                startActivity(i);

            }
        });


    }
}