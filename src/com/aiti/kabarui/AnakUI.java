package com.aiti.kabarui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class AnakUI extends Fragment {
	public TestFragmentAdapter mAdapter;
	public ViewPager mPager;
	public PageIndicator mIndicator;
	View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.main, container, false);

		mPager = (ViewPager) v.findViewById(R.id.pager);
		mAdapter = new TestFragmentAdapter(getActivity()
				.getSupportFragmentManager(), mPager);
		mPager.setAdapter(mAdapter);
		mIndicator = (TitlePageIndicator) v.findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		new setAdapterTask().execute();

		Intent i = new Intent();
		i.setAction("com.aiti.kabarui.start");
		getActivity().sendBroadcast(i);
		return v;
	}

	private class setAdapterTask extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
		}
	}

}