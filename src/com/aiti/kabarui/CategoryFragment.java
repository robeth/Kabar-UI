package com.aiti.kabarui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
	RelativeLayout waitLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rss_link = "http://www.anakui.com/category/beasiswa-lowongan/feed/";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.category, container, false);

		
		list = (ListView) v.findViewById(R.id.list_berita);
		waitLayout = (RelativeLayout) v.findViewById(R.id.wait_layout);
		new loadRSSFeedItems().execute(rss_link);
		// Getting adapter by passing xml data ArrayList
		return v;
	}
	
	/**
     * Background Async Task to get RSS Feed Items data from URL
     * */
    class loadRSSFeedItems extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(CategoryFragment.this.getActivity());
//            pDialog.setMessage("Loading recent articles...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }
 
        /**
         * getting all recent articles and showing them in listview
         * */
        @Override
        protected String doInBackground(String... args) {
            // rss link url
            String rss_url = args[0];
 
            // list of rss items
            rssItems = rssParser.getRSSFeedItems(rss_url);
 
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    list.setAdapter(new NewsAdapter(CategoryFragment.this.getActivity(),rssItems));
                }
            });
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

}