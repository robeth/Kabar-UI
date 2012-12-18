package com.aiti.kabarui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.aiti.rss.RSSItem;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String creator = getIntent().getStringExtra("creator");
		String pubDate = getIntent().getStringExtra("pubdate");
		String link = getIntent().getStringExtra("link");
		String description = getIntent().getStringExtra("description");
		String category = getIntent().getStringExtra("category");
		RSSItem item = new RSSItem(title, category, link, description, pubDate, "",
				creator);
		setContentView(R.layout.news);

		TextView tvTitle, tvCreator, tvPubDate, tvDescription;
		WebView wv;
		tvTitle = (TextView) findViewById(R.id.news_title);
		tvCreator = (TextView) findViewById(R.id.news_writer);
		tvPubDate = (TextView) findViewById(R.id.news_pubdate);
		tvDescription = (TextView) findViewById(R.id.news_description);
		wv = (WebView) findViewById(R.id.webview);

		tvTitle.setText(title);
		tvCreator.setText(creator);
		tvPubDate.setText(item.getFormattedDate());
		tvDescription.setText(description);
		wv.loadUrl(item.getLink());
	}

	

}
