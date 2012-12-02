package com.aiti.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

/**
 * This class handle RSS Item <item> node in rss xml
 * */
public class RSSItem {

	// All <item> node name
	String _title;
	String _link;
	String _description;
	String _pubdate;
	String _guid;
	String _creator;
	GregorianCalendar date;

	// constructor
	public RSSItem() {

	}

	// constructor with parameters
	public RSSItem(String title, String link, String description,
			String pubdate, String guid, String creator) {
		this._title = title;
		this._link = link;
		this._description = description;
		this._pubdate = pubdate;
		this._guid = guid;
		this._creator = creator;

		DateFormat formatter = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss zzz");
		date = (GregorianCalendar) formatter.getCalendar();
	}

	/**
	 * All SET methods
	 * */
	public void setTitle(String title) {
		this._title = title;
	}

	public void setLink(String link) {
		this._link = link;
	}

	public void setDescription(String description) {
		this._description = description;
	}

	public void setPubdate(String pubDate) {
		this._pubdate = pubDate;
	}

	public void setGuid(String guid) {
		this._guid = guid;
	}

	/**
	 * All GET methods
	 * */
	public String getTitle() {
		return this._title;
	}

	public String getLink() {
		return this._link;
	}

	public String getDescription() {
		return this._description;
	}

	public String getPubdate() {
		return this._pubdate;
	}

	public String getGuid() {
		return this._guid;
	}

	public String getCreator() {
		return _creator;
	}

	public void setCreator(String _creator) {
		this._creator = _creator;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	private String getDayOfTheWeek(int i) {
		String dayOfTheWeek = "";
		if (i == 2) {
			dayOfTheWeek = "Senin";
		} else if (i == 3) {
			dayOfTheWeek = "Selasa";
		} else if (i == 4) {
			dayOfTheWeek = "Rabu";
		} else if (i == 5) {
			dayOfTheWeek = "Kamis";
		} else if (i == 6) {
			dayOfTheWeek = "Jumat";
		} else if (i == 7) {
			dayOfTheWeek = "Sabtu";
		} else if (i == 1) {
			dayOfTheWeek = "Minggu";
		}
		return dayOfTheWeek;
	}

	private String getDateString(int day, int month, int year) {
		String m = "";
		switch (month) {
		case 1:
			m = "Januari";
			break;
		case 2:
			m = "Februari";
			break;
		case 3:
			m = "Maret";
			break;
		case 4:
			m = "April";
			break;
		case 5:
			m = "Mei";
			break;
		case 6:
			m = "Juni";
			break;
		case 7:
			m = "Juli";
			break;
		case 8:
			m = "Agustus";
			break;
		case 9:
			m = "September";
			break;
		case 10:
			m = "Oktober";
			break;
		case 11:
			m = "November";
			break;
		case 12:
			m = "Desember";
			break;
		}
		return "" + day + " " + m + " " + year;

	}

	public CharSequence getFormattedDate() {
		// TODO Auto-generated method stub
		return getDayOfTheWeek(getDate().get(Calendar.DAY_OF_WEEK))
				+ ", "
				+ getDateString(getDate().get(Calendar.DAY_OF_MONTH), getDate()
						.get(Calendar.MONTH), getDate().get(Calendar.YEAR));
	}

}
