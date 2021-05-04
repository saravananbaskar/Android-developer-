package com.e.system_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DisplayActivity extends AppCompatActivity {
    private EditText events;
    private CalendarView calendarView;
    private TextView disp_date , disp_time;
    private TimePicker timePicker;
    private Button pass_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        events = findViewById(R.id.event);
        calendarView = findViewById(R.id.calender);
        disp_date = findViewById(R.id.disp_date);
        disp_time = findViewById(R.id.disp_time);
        timePicker = findViewById(R.id.Time_clock);
        pass_next = findViewById(R.id.pass_next);

        //date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                disp_date.setText(date);
            }
        });

        //Clock

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(DisplayActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourofday);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        disp_time.setText(time);


                    }
                },hours ,mins,false);

                timePickerDialog.show();

            }
        });
        pass_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg ;
                String date ;
                String time ;
                msg = events.getText().toString();
                time = disp_time.getText().toString();
                date = disp_date.getText().toString();


                Intent pass = new Intent(DisplayActivity.this,EditActivity.class);
                pass.putExtra("msg",msg);
                pass.putExtra("date",date);
                pass.putExtra("time",time);
                startActivity(pass);

            }
        });



    }
}