package com.e.system_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private EditText event_show, time_show, date_show;
    private Button save_data, view_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        event_show = findViewById(R.id.event_show);
        time_show = findViewById(R.id.time_show);
        date_show = findViewById(R.id.date_show);
        save_data = findViewById(R.id.save_data);
        view_data = findViewById(R.id.view_data);







        String Tm = getIntent().getStringExtra("time");
        time_show.setText(Tm);
        final String ms = getIntent().getStringExtra("msg");
        event_show.setText(ms);
        String da = getIntent().getStringExtra("date");
        date_show.setText(da);
        final String eve = event_show.getText().toString();

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditActivity.this, View_db_Activity.class);
                startActivity(i);
            }
        });


        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();


            }
        });
    }

    public void insert() {
        try {
            String time = time_show.getText().toString();
            String msg = event_show.getText().toString();
            String date = date_show.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,time varchar , msg varchar , date varchar)");

            String sql = "insert into records(time , msg , date)values(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, time);
            statement.bindString(2, msg);
            statement.bindString(3, date);
            statement.execute();
            Toast.makeText(EditActivity.this, "Data saved", Toast.LENGTH_SHORT).show();

            time_show.setText("");
            event_show.setText("");
            date_show.setText("");
        } catch (Exception ex) {
            Toast.makeText(this, "Data fail", Toast.LENGTH_SHORT).show();
        }

    }
    }
