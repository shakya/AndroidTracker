package com.android.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
	private static String DB_PATH = "/data/data/com.android.data/databases/";
	private static String DB_NAME = "androidTrackerDB.sqlite";
	
	private SQLiteDatabase myDB;
	private final Context myContext;
	boolean dbExist = false;
	
	public DBHandler(Context context){
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}


	/*
	 * Creates a empty database on the system and rewrites it with the VEHIdroid
	 * database.
	 */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		if (dbExist) {
			/*
			 * database already exist
			 */
		} else {
			/*
			 * create an empty database rewrites that with the VEHIdroid db
			 */
			SQLiteDatabase sqdb = this.getReadableDatabase();

			try {
				/*
				 * overwrite the database
				 */
				copyDataBase();

			} catch (IOException e) {
				/*
				 * Error occured while copying
				 */
				throw new Error("Error copying database");

			}
			/*
			 * close db
			 */
			sqdb.close();
		}
	}

	/*
	 * Check if the database already exist and avoids re-copying
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			/*
			 * database does't exist yet.
			 */
		}

		if (checkDB != null) {

			checkDB.close();
			this.close();
		}

		if (checkDB != null)
			return true;
		else
			return false;

	}

	private void copyDataBase() throws IOException {

		String outFileName = DB_PATH + DB_NAME;

		// Open local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
