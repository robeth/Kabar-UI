package com.aiti.kabarui;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.aiti.rss.RSSItem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String creator = getIntent().getStringExtra("creator");
		String pubDate = getIntent().getStringExtra("pubdate");
		String link = getIntent().getStringExtra("link");
		String description = getIntent().getStringExtra("description");
		RSSItem item = new RSSItem(title, link, description, pubDate, "",
				creator);
		setContentView(R.layout.news);

		TextView tvTitle, tvCreator, tvPubDate, tvDescription;
		tvTitle = (TextView) findViewById(R.id.news_title);
		tvCreator = (TextView) findViewById(R.id.news_writer);
		tvPubDate = (TextView) findViewById(R.id.news_pubdate);
		tvDescription = (TextView) findViewById(R.id.news_description);

		tvTitle.setText(title);
		tvCreator.setText(creator);
		tvPubDate.setText(item.getFormattedDate());
		tvDescription.setText(description);
	}

	

}
