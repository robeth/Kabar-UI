package com.aiti.kabarui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	TextView[] tvs = new TextView[12];
	ViewPager mPager;
	private static final int[] BG = { R.drawable.bitmap_acara,
			R.drawable.bitmap_beasiswa, R.drawable.bitmap_lomba,
			R.drawable.bitmap_santai, R.drawable.bitmap_umum,
			R.drawable.bitmap_komunitas, R.drawable.bitmap_acara,
			R.drawable.bitmap_admin, R.drawable.bitmap_opinion,
			R.drawable.bitmap_organisasi, R.drawable.bitmap_announcement,
			R.drawable.bitmap_snapshot };

	public HomeFragment() {
		super();
	}

	public HomeFragment(ViewPager mPager) {
		super();
		this.mPager = mPager;
		this.setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home, container, false);
		tvs[0] = (TextView)((RelativeLayout) v.findViewById(R.id.news1)).getChildAt(0);
		tvs[0].setText(R.string.category1);
		tvs[1] = (TextView)((RelativeLayout) v.findViewById(R.id.news2)).getChildAt(0);
		tvs[1].setText(R.string.category2);
		tvs[2] = (TextView)((RelativeLayout) v.findViewById(R.id.news3)).getChildAt(0);
		tvs[2].setText(R.string.category3);
		tvs[3] = (TextView)((RelativeLayout) v.findViewById(R.id.news4)).getChildAt(0);
		tvs[3].setText(R.string.category4);
		tvs[4] = (TextView)((RelativeLayout) v.findViewById(R.id.news5)).getChildAt(0);
		tvs[4].setText(R.string.category5);
		tvs[5] = (TextView)((RelativeLayout) v.findViewById(R.id.news6)).getChildAt(0);
		tvs[5].setText(R.string.category6);
		tvs[6] = (TextView)((RelativeLayout) v.findViewById(R.id.news7)).getChildAt(0);
		tvs[6].setText(R.string.category7);
		tvs[7] = (TextView)((RelativeLayout) v.findViewById(R.id.news8)).getChildAt(0);
		tvs[7].setText(R.string.category8);
		tvs[8] = (TextView)((RelativeLayout) v.findViewById(R.id.news9)).getChildAt(0);
		tvs[8].setText(R.string.category9);
		tvs[9] = (TextView)((RelativeLayout) v.findViewById(R.id.news10)).getChildAt(0);
		tvs[9].setText(R.string.category10);
		tvs[10] = (TextView)((RelativeLayout) v.findViewById(R.id.news11)).getChildAt(0);
		tvs[10].setText(R.string.category11);
		tvs[11] = (TextView)((RelativeLayout) v.findViewById(R.id.news12)).getChildAt(0);
		tvs[11].setText(R.string.category12);
		
		for(int i = 0; i < 12; i++){
			RelativeLayout temp = (RelativeLayout)tvs[i].getParent();
			temp.setOnClickListener(getListener(i+1));
			temp.setBackgroundResource(BG[i]);
		}

		// TODO Auto-generated method stub
		return v;
	}

	private OnClickListener getListener(final int pageNumber) {
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(pageNumber);
			}
		};
		return clickListener;
	}

}
