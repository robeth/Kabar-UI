package com.aiti.kabarui.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ArticleTable {

	// Database table
	public static final String NAME = "article";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_LINK = "link";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_PUBDATE = "pubdate";
	public static final String COLUMN_GUID = "guid";
	public static final String COLUMN_CREATOR = "creator";
	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_TITLE,
			COLUMN_CATEGORY, COLUMN_LINK, COLUMN_DESCRIPTION, COLUMN_PUBDATE,
			COLUMN_GUID, COLUMN_CREATOR };

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " + NAME + "("
			+ COLUMN_ID + " integer primary key , " + COLUMN_TITLE
			+ " text not null, " + COLUMN_CATEGORY + " text not null,"
			+ COLUMN_LINK + " text not null," + COLUMN_DESCRIPTION
			+ " text not null," + COLUMN_PUBDATE + " text not null,"
			+ COLUMN_GUID + " text not null," + COLUMN_CREATOR
			+ " text not null" + ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ArticleTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + NAME);
		onCreate(database);
	}
}
