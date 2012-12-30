package com.aiti.kabarui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class KUIPreferencesActivity extends PreferenceActivity {
	
	public static final String KEY_SYNC="pref_key_auto_sync",
			KEY_ACCELEROMETER ="pref_key_accelerometer",
			KEY_AUDIO ="pref_key_audio";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isAutoSync = sharedPref.getBoolean(KUIPreferencesActivity.KEY_SYNC, true);
		boolean isAccelerometer = sharedPref.getBoolean(KUIPreferencesActivity.KEY_ACCELEROMETER, true);
		boolean isAudio = sharedPref.getBoolean(KUIPreferencesActivity.KEY_AUDIO, true);
		
		Log.e("SharedPref values","Sync-Acc-Audio:"+isAutoSync+"-"+isAccelerometer+","+isAudio);
	}

	
}
