package com.aiti.kabarui.autofetch;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	private static final long REPEAT_TIME = 1000 * 60*60*24;
	private int counter = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(OnBootReceiver.class.getName(), "Booting "+counter++);
		AlarmManager alarmService = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		
		Calendar cur_cal = new GregorianCalendar();
		cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
		cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
		cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
		
		
		Intent i = new Intent(context, FetchService.class);
		PendingIntent pending = PendingIntent.getService(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		
		alarmService.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), REPEAT_TIME, pending);

	}
}