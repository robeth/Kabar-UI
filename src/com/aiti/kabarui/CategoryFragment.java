package com.aiti.kabarui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aiti.kabarui.database.DatabaseModel;
import com.aiti.rss.RSSFeed;
import com.aiti.rss.RSSItem;
import com.aiti.rss.RSSParser;

public class CategoryFragment extends Fragment {

	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String, String>>();
	RSSParser rssParser = new RSSParser();
	List<RSSItem> rssItems = new ArrayList<RSSItem>();
	RSSFeed rssFeed;
	String rss_link;
	ListView list;
	DatabaseModel model;
	RelativeLayout waitLayout;

	public static final int CATEGORY_ACARA = 0, CATEGORY_BEASISWA = 1,
			CATEGORY_LOMBA = 2, CATEGORY_SANTAI = 3, CATEGORY_UMUM = 4,
			CATEGORY_KOMUNITAS = 5, CATEGORY_KAMU = 6, CATEGORY_ADMIN = 7,
			CATEGORY_OPINI = 8, CATEGORY_ORGANISASI = 9,
			CATEGORY_PENGUMUMAN = 10, CATEGORY_SNAPSHOT = 11;

	public static final String[] ANAKUI_CATEGORIES = { "Home", "Radio",
			"Acara Kampus", "Beasiswa", "Lomba", "Santai", "Umum",
			"Komunitas Mahasiswa", "Dari Kamu", "Dari Admin", "Opini",
			"Organisasi Mahasiswa", "Pengumuman Kampus", "Snapshot" };

	public static final String[] ANAKUI_LINKS = {
			"http://www.anakui.com/category/acara-kampus/feed/",
			"http://www.anakui.com/category/beasiswa-lowongan/feed/",
			"http://www.anakui.com/category/lomba-prestasi/feed/",
			"http://www.anakui.com/category/santai/feed/",
			"http://www.anakui.com/category/umum/feed/",
			"http://www.anakui.com/category/komunitas-mahasiswa/feed/",
			"http://www.anakui.com/category/berita-dari-kamu/feed/",
			"http://www.anakui.com/category/dari-admin/feed/",
			"http://www.anakui.com/category/opini/feed/",
			"http://www.anakui.com/category/organisasi-mahasiswa-ukm/feed/",
			"http://www.anakui.com/category/pengumuman-resmi-kampus/feed",
			"http://www.anakui.com/category/snapshot/feed" };
	private int category;

	public CategoryFragment() {
		super();
	}

	public CategoryFragment(int category) {
		super();
		this.category = category;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rss_link = ANAKUI_LINKS[this.category];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.category, container, false);

		list = (ListView) v.findViewById(R.id.list_berita);
		waitLayout = (RelativeLayout) v.findViewById(R.id.wait_layout);

		model = new DatabaseModel(getActivity());
		model.open();
		if (model.isCategoryExist(ANAKUI_CATEGORIES[category])) {
			rssItems = model.getAllItems(ANAKUI_CATEGORIES[category]);
			updateActivityUi();
			// Getting adapter by passing xml data ArrayList
		} else {
			new FetchAsyncTask().execute(rss_link);
		}

		return v;
	}

	private void updateActivityUi() {
		Collections.sort(rssItems, new ItemComparator());
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				list.setAdapter(new NewsAdapter(CategoryFragment.this
						.getActivity(), rssItems));
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent i = new Intent(CategoryFragment.this
								.getActivity(), NewsActivity.class);
						i.putExtra("title", rssItems.get(arg2).getTitle());
						i.putExtra("creator", rssItems.get(arg2).getCreator());
						i.putExtra("pubdate", rssItems.get(arg2).getPubdate());
						i.putExtra("link", rssItems.get(arg2).getLink());
						i.putExtra("description", rssItems.get(arg2)
								.getDescription());
						i.putExtra("category", rssItems.get(arg2).getCategory());
						CategoryFragment.this.getActivity().startActivity(i);
					}

				});
				model.close();
				waitLayout.setVisibility(View.INVISIBLE);
			}
		});

	}

	/**
	 * Background Async Task to get RSS Feed Items data from URL
	 * */
	class FetchAsyncTask extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new
			// ProgressDialog(CategoryFragment.this.getActivity());
			// pDialog.setMessage("Loading recent articles...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		/**
		 * getting all recent articles and showing them in listview
		 * */
		@Override
		protected String doInBackground(String... args) {
			// rss link url
			String rss_url = args[0];

			// list of rss items
			rssItems = rssParser.getRSSFeedItems(rss_url,
					ANAKUI_CATEGORIES[category]);
			for (int i = 0; i < rssItems.size(); i++) {
				model.addItem(rssItems.get(i));
			}
			
			// updating UI from Background Thread
			if (getActivity() != null) {
				updateActivityUi();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			waitLayout.setVisibility(View.INVISIBLE);
		}
	}

	private class ItemComparator implements Comparator<RSSItem> {

		@Override
		public int compare(RSSItem lhs, RSSItem rhs) {
			return -1 * lhs.getDate().compareTo(rhs.getDate());
		}
	}

}