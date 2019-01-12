package com.codingblocks.msitnotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent i=new Intent(getBaseContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getBaseContext(),12345,i,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+5000,pi);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }
}
