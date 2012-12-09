package com.aiti.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.util.Log;

/**
 * This class handle RSS Item <item> node in rss xml
 * */
public class RSSItem {

	// All <item> node name
	String _title;
	String _category;
	String _link;
	String _description;
	String _pubdate;
	String _guid;
	String _creator;
	GregorianCalendar date;

	// constructor with parameters
	public RSSItem(String title, String category, String link,
			String description, String pubdate, String guid, String creator) {
		this._title = title;
		this._category = category;
		this._link = link;
		this._description = description;
		this._pubdate = pubdate;
		this._guid = guid;
		this._creator = creator;

		DateFormat formatter = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		Date d = null;
		try {
			d = formatter.parse(pubdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		date = (GregorianCalendar) Calendar.getInstance();
		date.setTime(d);
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this._title);
		builder.append("-");
		builder.append(getFormattedDate());
		builder.append("-");
		builder.append(_pubdate);

		return builder.toString();
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

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		this._category = _category;
	}

	private String getDateString(int day, int month, int year) {
		String m = "";
		switch (month) {
		case Calendar.JANUARY:
			m = "Januari";
			break;
		case Calendar.FEBRUARY:
			m = "Februari";
			break;
		case Calendar.MARCH:
			m = "Maret";
			break;
		case Calendar.APRIL:
			m = "April";
			break;
		case Calendar.MAY:
			m = "Mei";
			break;
		case Calendar.JUNE:
			m = "Juni";
			break;
		case Calendar.JULY:
			m = "Juli";
			break;
		case Calendar.AUGUST:
			m = "Agustus";
			break;
		case Calendar.SEPTEMBER:
			m = "September";
			break;
		case Calendar.OCTOBER:
			m = "Oktober";
			break;
		case Calendar.NOVEMBER:
			m = "November";
			break;
		case Calendar.DECEMBER:
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

	public int getID() {
		StringBuilder builder = new StringBuilder();
		builder.append(_title);
		builder.append(_pubdate);
		builder.append(_category);
		
		return builder.toString().hashCode();
	}

}
