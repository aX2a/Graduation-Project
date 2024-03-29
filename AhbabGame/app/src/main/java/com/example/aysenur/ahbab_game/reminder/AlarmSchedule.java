package com.example.aysenur.ahbab_game.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getBroadcast;

public class AlarmSchedule {

    public void setAlarm(Context context, Intent intent, Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getBroadcast(context, 100, intent, FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public int diffDate (String addedDate, String addedTime ) throws ParseException {

        String total = addedDate + " " + addedTime;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date time = formatter.parse(total);

        Date currentTime = Calendar.getInstance().getTime();

        long diff = time.getTime() - currentTime.getTime();
        int Hours = (int) (diff/(1000 * 60 * 60) * 60);
        int Mins = (int) (diff/(1000*60)) % 60;
        int totalTime = Hours + Mins + 1;
        Log.i("diff: ", ""+ totalTime + ", " + time + ", c: " + currentTime);

        return totalTime;
    }

}
