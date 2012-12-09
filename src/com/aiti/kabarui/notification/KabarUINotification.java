package com.aiti.kabarui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.aiti.kabarui.KabarUI;
import com.aiti.kabarui.R;

public class KabarUINotification {
	public static void createNotification(Context context, int updateArticle,
			int updateCategory, String message) {
		Intent intent = new Intent(context, KabarUI.class);
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);

		// Build notification
		// Actions are just fake
		Notification noti = new NotificationCompat.Builder(context)
				.setContentTitle("Kabar UI - "+message)
				.setContentText(
						"" + updateArticle + " artikel baru pada "
								+ updateCategory + " kategori.")
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
				.build();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}
}
