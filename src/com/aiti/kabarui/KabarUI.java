package com.aiti.kabarui;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.aiti.kabarui.autofetch.FetchService;
import com.aiti.kabarui.experiment.ReclickableTabHost;
import com.aiti.kabarui.experiment.ReclickableTabHost.OnCurrentItemListenerClick;

public class KabarUI extends SherlockFragmentActivity implements
		TabHost.OnTabChangeListener {

	private ReclickableTabHost mTabHost;

	private static final String TAG = "FragmentTabs";
	public static final String TAB_ANAKUI = "anakui";
	public static final String TAB_RTCUI = "rtcui";
	private static final int CODE_RESULT_VOICE = 1234;
	private static final int CODE_RESULT_SETTING = 100;
	private Menu myMenu;
	private int mCurrentTab;
	private boolean prevSyncSetting;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kabarui);
		mTabHost = (ReclickableTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_ANAKUI, R.drawable.anakui_item, R.id.tab_1));
		mTabHost.addTab(newTab(TAB_RTCUI, R.drawable.radio_item, R.id.tab_2));

		if (savedInstanceState != null
				&& savedInstanceState.containsKey("mCurrentTab"))
			mCurrentTab = savedInstanceState.getInt("mCurrentTab");
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setOnCurrentItemListenerClick(new OnCurrentItemListenerClick() {

			@Override
			public void onCurrentClick(int index) {
				// TODO Auto-generated method stub
				if (index == 0) {
					AnakUI f = (AnakUI) KabarUI.this
							.getSupportFragmentManager().findFragmentByTag(
									TAB_ANAKUI);
					if (f != null && f.mPager != null)
						f.mPager.setCurrentItem(0);
				}
			}
		});

		mTabHost.setCurrentTab(mCurrentTab);
		updateTab(TAB_ANAKUI, R.id.tab_1);

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean isSync = sp.getBoolean(KUIPreferencesActivity.KEY_SYNC, true);
		if (isSync) {
//			Intent i = new Intent();
//			i.setAction("com.aiti.kabarui.start");
//			sendBroadcast(i);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("mCurrentTab", mCurrentTab);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey("mCurrentTab"))
			mCurrentTab = savedInstanceState.getInt("mCurrentTab");
	}

	private TabSpec newTab(String tag, int imageID, int tabContentId) {
		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		View tabItem = getLayoutInflater().inflate(R.layout.tab_item, null,
				false);
		ImageView tabItemImageView = (ImageView) tabItem
				.findViewById(R.id.tabItemImage);
		tabItemImageView.setImageResource(imageID);
		tabSpec.setIndicator(tabItem);
		tabSpec.setContent(tabContentId);

		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_ANAKUI.equals(tabId)) {
			updateTab(tabId, R.id.tab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_RTCUI.equals(tabId)) {
			updateTab(tabId, R.id.tab_2);
			mCurrentTab = 1;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = this.getSupportFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			Fragment substitutionFragment = null;
			if (tabId.equals(TAB_ANAKUI)) {
				substitutionFragment = new AnakUI();
			} else {
				substitutionFragment = new RadioFragment();
			}
			fm.beginTransaction()
					.replace(placeholder, substitutionFragment, tabId).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Used to put dark icons on light action bar
		// boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
		if (myMenu == null) {
			myMenu = menu;
		}
		// menu.add("Search").setIcon(R.drawable.ic_action_microphone)
		// .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//
		// menu.add("Sync")
		// .setIcon(R.drawable.navigation_refresh)
		// .setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_IF_ROOM
		// | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		//
		// menu.add("Setting")
		// .setIcon(R.drawable.action_settings)
		// .setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_IF_ROOM
		// | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.home, menu);
		return true;
	}

	public void ketikaTombolDiTekan(View v) {
		mulaiAktivitasSuaraJadiTeks();
	}

	// mulai aktivitas voice recognition dengan
	// menghidupkan Intent-nya.

	private void mulaiAktivitasSuaraJadiTeks() {
		Intent ubahSuaraJadiTeks = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		ubahSuaraJadiTeks.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		ubahSuaraJadiTeks.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Demo suara jadi teks...");
		startActivityForResult(ubahSuaraJadiTeks, CODE_RESULT_VOICE);
	}

	// Olah hasil dari Intent.

	@Override
	protected void onActivityResult(int kodePriksa, int ambilHasil, Intent hasil) {
		if (kodePriksa == CODE_RESULT_VOICE && ambilHasil == RESULT_OK) {
			ArrayList<String> kataYangCocok = hasil
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			int halaman = cekKebenarannya(kataYangCocok);
			AnakUI f = (AnakUI) KabarUI.this.getSupportFragmentManager()
					.findFragmentByTag(TAB_ANAKUI);
			if (f != null && f.mPager != null)
				f.mPager.setCurrentItem(halaman + 1);

		} else if (kodePriksa == CODE_RESULT_SETTING) {
			invalidateOptionsMenu();
			updateAccelerometerOption();
			updateSync();
		}
		super.onActivityResult(kodePriksa, ambilHasil, hasil);
	}

	private void updateSync() {
		// TODO Auto-generated method stub
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean isSync = sp.getBoolean(KUIPreferencesActivity.KEY_SYNC, true);

		if (isSync != prevSyncSetting) {
			if (!isSync) {
				AlarmManager alarmService = (AlarmManager) this
						.getSystemService(Context.ALARM_SERVICE);

				Intent i = new Intent(this, FetchService.class);
				PendingIntent pending = PendingIntent.getService(this, 0, i,
						PendingIntent.FLAG_CANCEL_CURRENT);
				alarmService.cancel(pending);
			} else {
				Intent i = new Intent();
				i.setAction("com.aiti.kabarui.start");
				sendBroadcast(i);
			}
		}

	}

	private void updateAccelerometerOption() {
		// TODO Auto-generated method stub
		AnakUI f = (AnakUI) KabarUI.this.getSupportFragmentManager()
				.findFragmentByTag(TAB_ANAKUI);

		if (f != null && f.mPager != null) {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);
			boolean isAccelerometer = sp.getBoolean(
					KUIPreferencesActivity.KEY_ACCELEROMETER, true);
			f.setAccelerometer(isAccelerometer);
		}
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean isAudio = sp.getBoolean(KUIPreferencesActivity.KEY_AUDIO, true);

		MenuItem audioItem = myMenu.findItem(R.id.menuitem_audio);
		audioItem.setVisible(isAudio);
		return true;
	}

	private int cekKebenarannya(ArrayList<String> ktc) {
		// TODO Auto-generated method stub
		for (int i = 0; i < CategoryFragment.ANAKUI_SUARA.length; i++) {
			String[] suaras = CategoryFragment.ANAKUI_SUARA[i];
			for (int j = 0; j < suaras.length; j++) {
				for (int k = 0; k < ktc.size(); k++) {
					if (ktc.get(k).equals(suaras[j])) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Log.d("item", "item:" + item.getTitle() + "--"
				+ R.string.menuitem_audio + "--" + R.string.menuitem_setting
				+ "--" + R.string.menuitem_sync);
		if (item.getTitle().equals(
				getResources().getString(R.string.menuitem_audio))) {
			ketikaTombolDiTekan(null);
		} else if (item.getTitle().equals(
				getResources().getString(R.string.menuitem_setting))) {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);
			prevSyncSetting = sp.getBoolean(KUIPreferencesActivity.KEY_SYNC,
					true);
			Intent i = new Intent(this, KUIPreferencesActivity.class);
			startActivityForResult(i, 100);
		} else if (item.getTitle().equals(
				getResources().getString(R.string.menuitem_sync))) {
			AnakUI f = (AnakUI) KabarUI.this.getSupportFragmentManager()
					.findFragmentByTag(TAB_ANAKUI);
			if (f != null && f.mPager != null) {
				int cur = f.mPager.getCurrentItem();
				if (cur == 0) {
					Toast.makeText(this, "Please go to category you want to synchronize first", Toast.LENGTH_LONG).show();
				} else {
					CategoryFragment cf = (CategoryFragment) f.mPager
							.getAdapter().instantiateItem(f.mPager, cur);

					Log.d("Synch now", "from hell");
					cf.SynchNow();
				}
			}
		}

		return true;
	}

}
