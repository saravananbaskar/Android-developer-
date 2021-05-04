package com.e.system_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private int notificationId = 1;
    TextToSpeech textToSpeech;
    TextView disp_time1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Set onClick Listener
        findViewById(R.id.setBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
        findViewById(R.id.disp_time1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final EditText editText = findViewById(R.id.editText);
        TimePicker timePicker = findViewById(R.id.Time_clock1);



        // Intent
        Intent intent = new Intent(HomeActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", editText.getText().toString());

        // PendingIntent
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                HomeActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
//************************************************************************************************
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int mins = calendar.get(Calendar.MINUTE);


                        TimePickerDialog timePickerDialog = new TimePickerDialog(HomeActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hour);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                                String time = format.format(c.getTime());
                                disp_time1.setText(time);
                                long alarmStartTime =c.getTimeInMillis();

                                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);

                            }
                        },hours ,mins,false);

                        timePickerDialog.show();

                    }
                });
//************************************************************************************************
                // Create time.




                // Set Alarm
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                Intent p = new Intent(HomeActivity.this,DisplayActivity.class);
                startActivity(p);


                // speech
                textToSpeech = new TextToSpeech(getApplicationContext(),
                        new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int i) {
                                if (i==TextToSpeech.SUCCESS){
                                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                                    String s = editText.getText().toString();

                                    int speech = textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);

                                }
                            }
                        });

                break;

            case R.id.cancelBtn:
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                break;
        }


    }
}