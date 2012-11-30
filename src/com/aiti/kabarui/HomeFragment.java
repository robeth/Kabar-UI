package com.aiti.kabarui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	KabarUI activity;
	TextView tv1, tv2, tv3, tv4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (KabarUI)getActivity();
	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home, container, false);
		tv1 = (TextView) v.findViewById(R.id.category1);
		tv2 = (TextView) v.findViewById(R.id.category2);
		tv3 = (TextView) v.findViewById(R.id.category3);
		tv4 = (TextView) v.findViewById(R.id.category4);
		tv1.setOnClickListener(getListener(1));
		tv2.setOnClickListener(getListener(2));
		tv3.setOnClickListener(getListener(3));
		tv4.setOnClickListener(getListener(4));
		// TODO Auto-generated method stub
		return v;
	}



	private OnClickListener getListener(final int pageNumber){
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.mPager.setCurrentItem(pageNumber);
			}
		};
		return clickListener;
	}
	

}
