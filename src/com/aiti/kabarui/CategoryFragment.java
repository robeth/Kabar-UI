package com.aiti.kabarui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aiti.kabarui.rss.RssReader;

public class CategoryFragment extends Fragment {
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	RssReader rssReader;
	ListView list;
	NewsAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			// Create RSS reader
			rssReader = new RssReader("http://www.anakui.com/category/beasiswa-lowongan/feed/");
			// Get a ListView from main view

		} catch (Exception e) {
			Log.e("ITCRssReader", e.getMessage());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.category, container, false);
		list = (ListView) v.findViewById(R.id.list_berita);
		try {
			adapter = new NewsAdapter(getActivity(), rssReader.getItems());
		} catch (Exception e) {
			Log.e("ITCRssReader", e.getMessage());
		}
		list.setAdapter(adapter);

		return v;
	}

}