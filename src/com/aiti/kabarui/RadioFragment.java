package com.aiti.kabarui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RadioFragment extends Fragment {
	ImageView i1, i2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.radiortc, container, false);
		i1 = (ImageView) v.findViewById(R.id.im1);

		// findViewById(R.id.seekBar1).setPivotX((float)findViewById(R.id.seekBar1).getWidth()/2);
		// findViewById(R.id.seekBar1).setPivotY((float)findViewById(R.id.seekBar1).getHeight()/2);
		// findViewById(R.id.seekBar1).setRotation(90);
		i1.setOnClickListener(new OnClickListener() {
			boolean state = true;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (state)
					i1.setImageResource(R.drawable.btn_radio_off);
				else
					i1.setImageResource(R.drawable.btn_radio_on);

				state = !state;
			}
		});
		return v;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

}
