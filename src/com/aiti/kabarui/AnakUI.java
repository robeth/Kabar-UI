package com.aiti.kabarui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class AnakUI extends Fragment implements SensorEventListener {
	public TestFragmentAdapter mAdapter;
	public ViewPager mPager;
	public PageIndicator mIndicator;
	private View v;
	private float mLastX, mLastY, mLastZ;
	private static final float thresX = 2, resetX = 0.5f;
	private boolean hasReset = true, isNext = false;
	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private boolean accelerometerStatus, prevStatus;

	private final float NOISE = (float) 2.0;

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

		mInitialized = false;

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		accelerometerStatus = sp.getBoolean(
				KUIPreferencesActivity.KEY_ACCELEROMETER, true);

		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		setAccelerometer(accelerometerStatus);

		
		return v;
	}

	public void onResume() {

		super.onResume();
		if (accelerometerStatus) {
			mSensorManager.registerListener(this, mAccelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

	}

	public void onPause() {

		super.onPause();
		if(accelerometerStatus){
			mSensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			mInitialized = true;

		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);

			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			mLastX = x;
			mLastY = y;
			mLastZ = z;

		}

		if (hasReset) {
			if (x < -thresX) {
				// prev
				int currentPageIndex = mPager.getCurrentItem();
				if (currentPageIndex > 0) {
					mPager.setCurrentItem(--currentPageIndex);
				}
				hasReset = false;
				isNext = false;
			} else if (x > thresX) {
				// next
				int currentPageIndex = mPager.getCurrentItem();
				if (currentPageIndex < mAdapter.getCount() - 1) {
					mPager.setCurrentItem(++currentPageIndex);
				}
				hasReset = false;
				isNext = true;
			}
		} else {
			if (isNext && x < resetX) {
				hasReset = true;
			} else if (!isNext && x > -resetX) {
				hasReset = true;
			}
		}
	}

	public void setAccelerometer(boolean isAccelerometer) {
		// TODO Auto-generated method stub
		accelerometerStatus = isAccelerometer;
		if (accelerometerStatus)
			mSensorManager.registerListener(this, mAccelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
		else
			mSensorManager.unregisterListener(this);

	}
}