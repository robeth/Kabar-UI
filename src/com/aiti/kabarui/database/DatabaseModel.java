package com.aiti.kabarui.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aiti.rss.RSSItem;

public class DatabaseModel {

	// Database fields
	private SQLiteDatabase database;
	private KabarUIDatabaseHelper dbHelper;

	public DatabaseModel(Context context) {
		dbHelper = new KabarUIDatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean addItem(RSSItem item) {
		ContentValues values = new ContentValues();
		values.put(ArticleTable.COLUMN_ID, item.getID());
		values.put(ArticleTable.COLUMN_TITLE, item.getTitle());
		values.put(ArticleTable.COLUMN_CATEGORY, item.getCategory());
		values.put(ArticleTable.COLUMN_CREATOR, item.getCreator());
		values.put(ArticleTable.COLUMN_DESCRIPTION, item.getDescription());
		values.put(ArticleTable.COLUMN_GUID, item.getGuid());
		values.put(ArticleTable.COLUMN_LINK, item.getLink());
		values.put(ArticleTable.COLUMN_PUBDATE, item.getPubdate());
		long insertId = -1;
		
		try{
		insertId = database.insert(ArticleTable.NAME, null, values);
		Log.d("addItem", "Status:" + insertId);
		} catch (Exception e){
			insertId = -1;
			Log.d("fail addItem", "Status:" + insertId);
		}
		
		return insertId != -1;
	}

	public int deleteItem(RSSItem item) {
		long id = item.getID();

		int affectedRows = database.delete(ArticleTable.NAME,
				ArticleTable.COLUMN_ID + " = " + id, null);
		Log.d("deleteItem", "Status:" + affectedRows);
		return affectedRows;
	}

	public List<RSSItem> getAllItems() {
		List<RSSItem> items = new ArrayList<RSSItem>();

		Cursor cursor = database.query(ArticleTable.NAME,
				ArticleTable.ALL_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			RSSItem item = cursorToItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return items;
	}

	public List<RSSItem> getAllItems(String category) {
		List<RSSItem> items = new ArrayList<RSSItem>();
		Cursor cursor = database.query(ArticleTable.NAME,
				ArticleTable.ALL_COLUMNS, ArticleTable.COLUMN_CATEGORY + "='"
						+ category + "'", null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			RSSItem item = cursorToItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		return items;
	}

	private RSSItem cursorToItem(Cursor cursor) {
		RSSItem item = new RSSItem(cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5),
				cursor.getString(6), cursor.getString(7));
		return item;
	}

	public boolean isCategoryExist(String category) {
		Cursor cursor = database.query(ArticleTable.NAME,
				ArticleTable.ALL_COLUMNS, ArticleTable.COLUMN_CATEGORY + "= '"
						+ category + "'", null, null, null, null);
		boolean result = cursor.getCount() > 0;
		cursor.close();
		return result;
	}
	
	public boolean isItemExist(int id){
		Cursor cursor = database.query(ArticleTable.NAME,
				ArticleTable.ALL_COLUMNS, ArticleTable.COLUMN_ID + "="
						+ id, null, null, null, null);
		boolean result = cursor.getCount() > 0;
		cursor.close();
		return result;
	}
	

	public int getUpdatedArticle() {
		// TODO Auto-generated method stub
		List<RSSItem> items = getAllItems();
		GregorianCalendar c = (GregorianCalendar)Calendar.getInstance();
		int counter = 0;
		for(int i = 0; i < items.size(); i++){
			if(isSameDay(items.get(i).getDate(), c)){
				counter++;
			}
		}
		return counter;
	}
	
	private boolean isSameDay (GregorianCalendar c1, GregorianCalendar c2){
		return c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) &&
				c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
				c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
	}
}
