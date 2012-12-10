package com.aiti.kabarui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	TextView[] tvs = new TextView[12];
	ViewPager mPager;
	public HomeFragment(ViewPager mPager){
		super();
		this.mPager = mPager;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home, container, false);
		tvs[0] = (TextView) v.findViewById(R.id.category1);
		tvs[1] = (TextView) v.findViewById(R.id.category2);
		tvs[2] = (TextView) v.findViewById(R.id.category3);
		tvs[3] = (TextView) v.findViewById(R.id.category4);
		tvs[4]= (TextView) v.findViewById(R.id.category5);
		tvs[5] = (TextView) v.findViewById(R.id.category6);
		tvs[6] = (TextView) v.findViewById(R.id.category7);
		tvs[7] = (TextView) v.findViewById(R.id.category8);
		tvs[8] = (TextView) v.findViewById(R.id.category9);
		tvs[9] = (TextView) v.findViewById(R.id.category10);
		tvs[10] = (TextView) v.findViewById(R.id.category11);
		tvs[11] = (TextView) v.findViewById(R.id.category12);
		
		for(int i = 0; i < 12; i++){
			tvs[i].setOnClickListener(getListener(i+1));
		}

		// TODO Auto-generated method stub
		return v;
	}



	private OnClickListener getListener(final int pageNumber){
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(pageNumber);
			}
		};
		return clickListener;
	}
	

}
