package com.aiti.kabarui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
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
		RSSItem item = new RSSItem(title, category, link, description, pubDate,
				"", creator);
		setContentView(R.layout.news);

		TextView tvTitle, tvCreator, tvPubDate, tvDescription;
		WebView wv;
//		tvTitle = (TextView) findViewById(R.id.news_title);
//		tvCreator = (TextView) findViewById(R.id.news_writer);
//		tvPubDate = (TextView) findViewById(R.id.news_pubdate);
//		tvDescription = (TextView) findViewById(R.id.news_description);
		wv = (WebView) findViewById(R.id.webview);

//		tvTitle.setText(title);
//		tvCreator.setText(creator);
//		tvPubDate.setText(item.getFormattedDate());
//		tvDescription.setText(Html.fromHtml(description));
		wv.getSettings().setJavaScriptEnabled(true);
		Log.d("Link", item.getLink());
//		new FetchAsyncTask().execute("http://www.google.com");
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean isExperimental = sp.getBoolean(
				"pref_key_exp", true);
		
		wv.loadUrl((isExperimental) ? "http://www.ui.ac.id":item.getLink());
	}

	private String getContentFromURL(String url) {
		// RSS url
		String rss_url = null;

		try {
			// Using JSoup library to parse the html source code
			Log.d("STARTTTTTTTTTTTTT", "YEAHHHHH:" + url);
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			// finding rss links which are having link[type=application/rss+xml]

			Log.d("ID Link Post", " " + doc.html());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	class FetchAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... args) {
			//getContentFromURL(args[0]);
			asusila(args[0]);
			return null;
		}

		@Override
		protected void onPostExecute(String args) {
		}
	}

	/**
	 * Method to get xml content from url HTTP Get request
	 * */
	public String getXmlFromUrl(String url) {
		String xml = null;

		try {
			// request method is GET
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("Entity XML" + url, xml);
		// return XML
		return xml;
	}

	public void asusila(String urlString) {
		URL url = null;
		HttpURLConnection urlConnection = null;
		
		try {
			url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			Writer writer = new StringWriter();

			char[] buffer = new char[102400];

			Reader reader = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
			Log.d("This is it", writer.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
