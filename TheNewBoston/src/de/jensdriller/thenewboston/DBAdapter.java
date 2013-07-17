package de.jensdriller.thenewboston;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// Orientated on: http://www.devx.com/wireless/Article/40842/1763
public class DBAdapter {

	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_HOTNESS = "hotness";

	public static final String DATABASE_NAME = "HotOrNotDB";
	public static final String DATABASE_TABLE = "babes";
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT NOT NULL, " + KEY_HOTNESS
			+ " TEXT NOT NULL);";

	//private final Context context;
	private DBHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context context) {

		//this.context = context;
		DBHelper = new DBHelper(context);

	}

	//---opens the database---
	public DBAdapter open() {

		this.db = DBHelper.getWritableDatabase();
		return this;

	}

	//---closes the database---  
	public void close() {

		DBHelper.close();

	}

	// return the row ID of the newly inserted row, or -1 if an error occurred
	public long insertEntry(String name, String hotness) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_NAME, name);
		contentValues.put(KEY_HOTNESS, hotness);
		return this.db.insert(DATABASE_TABLE, null, contentValues);

	}

	public Cursor getAllBabes() {

		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_HOTNESS }, null, null, null, null, null);

	}

	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.w("INFO:", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);

		}

	}

	public Cursor getBabeForId(long id) throws Exception {

		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_HOTNESS }, KEY_ROWID + "=" + id, null, null, null, null);

	}

	public void updateEntry(long id, String name, String hotness) throws Exception {

		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_HOTNESS, hotness);
		db.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + id, null);

	}

	public void deleteEntry(long id) throws Exception {

		db.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);

	}

}
