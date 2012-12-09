package com.aiti.kabarui.autofetch;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	// Restart service every 30 seconds
	private static final long REPEAT_TIME = 1000 * 60 * 5;
	private int counter = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(OnBootReceiver.class.getName(), "Booting "+counter++);
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		
		//Create pending intent to be called regularly
//		Intent i = new Intent(context, OnRepeatReceiver.class);
		Intent i = new Intent(context, FetchService.class);
		PendingIntent pending = PendingIntent.getService(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 30);
		
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), REPEAT_TIME, pending);

	}
}