package com.aiti.kabarui.autofetch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.aiti.kabarui.CategoryFragment;
import com.aiti.kabarui.database.DatabaseModel;
import com.aiti.kabarui.notification.KabarUINotification;
import com.aiti.rss.RSSItem;
import com.aiti.rss.RSSParser;

public class FetchService extends Service {
	private final IBinder mBinder = new MyBinder();
	RSSParser rssParser = new RSSParser();
	List<RSSItem> rssItems = new ArrayList<RSSItem>();
	DatabaseModel model;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		model = new DatabaseModel(getApplicationContext());
		model.open();
		new FetchAsyncTask().execute();
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		FetchService getService() {
			return FetchService.this;
		}
	}

	class FetchAsyncTask extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new
			// ProgressDialog(CategoryFragment.this.getActivity());
			// pDialog.setMessage("Loading recent articles...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		/**
		 * getting all recent articles and showing them in listview
		 * */
		@Override
		protected String doInBackground(String... args) {
			for (int i = 0; i < CategoryFragment.ANAKUI_LINKS.length; i++) {
				Log.d("Background" + i, "Checking: "
						+ CategoryFragment.ANAKUI_LINKS[i]);
				rssItems = rssParser.getRSSFeedItems(
						CategoryFragment.ANAKUI_LINKS[i],
						CategoryFragment.ANAKUI_CATEGORIES[i]);
				for (int j = 0; j < rssItems.size(); j++) {
					if (!model.isItemExist(rssItems.get(j).getID()))
						model.addItem(rssItems.get(j));
				}
				Log.d("Background" + i, "Finished");
			}
			int updatedArticle = model.getUpdatedArticle();
			int updatedCategory = 1;
			GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
			KabarUINotification.createNotification(
					FetchService.this,
					updatedArticle,
					updatedCategory,
					g.get(Calendar.DAY_OF_MONTH) + "-" + g.get(Calendar.MONTH)
							+ " " + g.get(Calendar.HOUR_OF_DAY) + ":"
							+ g.get(Calendar.MINUTE));
			model.close();

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			// waitLayout.setVisibility(View.INVISIBLE);
			super.onPostExecute(args);
		}
	}

}