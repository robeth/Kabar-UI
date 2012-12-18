package com.aiti.kabarui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.aiti.kabarui.experiment.ReclickableTabHost;
import com.aiti.kabarui.experiment.ReclickableTabHost.OnCurrentItemListenerClick;

public class KabarUI extends FragmentActivity implements
		TabHost.OnTabChangeListener {

	private ReclickableTabHost mTabHost;

	private static final String TAG = "FragmentTabs";
	public static final String TAB_ANAKUI = "anakui";
	public static final String TAB_RTCUI = "rtcui";

	private int mCurrentTab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kabarui);
		mTabHost = (ReclickableTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_ANAKUI, R.drawable.anakui , R.id.tab_1));
		mTabHost.addTab(newTab(TAB_RTCUI, R.drawable.rtcui, R.id.tab_2));

		if (savedInstanceState != null
				&& savedInstanceState.containsKey("mCurrentTab"))
			mCurrentTab = savedInstanceState.getInt("mCurrentTab");
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setOnCurrentItemListenerClick(new OnCurrentItemListenerClick() {
			
			@Override
			public void onCurrentClick(int index) {
				// TODO Auto-generated method stub
				if(index == 0 ){
					AnakUI f = (AnakUI) KabarUI.this.getSupportFragmentManager().findFragmentByTag(TAB_ANAKUI);
					if(f != null && f.mPager!=null)
						f.mPager.setCurrentItem(0);
				}
			}
		});
		
		mTabHost.setCurrentTab(mCurrentTab);
		updateTab(TAB_ANAKUI, R.id.tab_1);
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
		View tabItem = getLayoutInflater().inflate(R.layout.tab_item, null, false);
		ImageView tabItemImageView = (ImageView)tabItem.findViewById(R.id.tabItemImage);
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

}
