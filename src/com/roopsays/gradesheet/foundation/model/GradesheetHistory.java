package com.roopsays.gradesheet.foundation.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GradesheetHistory extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "GradesheetHistory";
	private static final int DATABASE_VERSION = 1;
    private static final String GRADESHEET_HISTORY_TABLE_NAME = "gradesheet_history";
    private static final String GRADESHEET_HISTORY_TABLE_CREATE =
                "CREATE TABLE " + GRADESHEET_HISTORY_TABLE_NAME + " (" +
                "NUMBER_OF_QUESTIONS" + " TEXT PRIMARY KEY, " +
                "NUMBER_OF_TIMES_ACCESSED" + " INTEGER, " +
                "LAST_ACCESSED" + " TEXT);";
    
   
	
	public GradesheetHistory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	public void updateUsage(int numberOfQuestions) {
		SQLiteDatabase db = getWritableDatabase();
		
		String[] columns = new String[1];
		columns[0] = "NUMBER_OF_TIMES_ACCESSED";
		
		String[] values = new String[1];
		values[0] = numberOfQuestions + "";
		
		Cursor cursor = db.query(GRADESHEET_HISTORY_TABLE_NAME, columns, "NUMBER_OF_QUESTIONS = ?", values, null, null, null);
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int numberOfTimesAccessed = cursor.getInt(0);

			ContentValues updateValues = new ContentValues();
			updateValues.put("NUMBER_OF_TIMES_ACCESSED", numberOfTimesAccessed+1);

			db.update(GRADESHEET_HISTORY_TABLE_NAME, updateValues, "NUMBER_OF_QUESTIONS = ?" , values);
		} else {
			ContentValues insertValues = new ContentValues();
			insertValues.put("NUMBER_OF_QUESTIONS", numberOfQuestions);
			insertValues.put("NUMBER_OF_TIMES_ACCESSED", 1);
			insertValues.put("LAST_ACCESSED", 1);

			
			db.insert(GRADESHEET_HISTORY_TABLE_NAME, null, insertValues);
		}
		
	
		db.close();
	}

	public List<GradesheetMeta> topGradesheetRequests() {
		SQLiteDatabase db = getReadableDatabase();
		
		List<GradesheetMeta> results = new ArrayList<GradesheetMeta>();
		
		String[] columns = new String[3];
		columns[0] = "NUMBER_OF_QUESTIONS";
		columns[1] = "NUMBER_OF_TIMES_ACCESSED";
		columns[2] = "LAST_ACCESSED";

		
		Cursor cursor = db.query(GRADESHEET_HISTORY_TABLE_NAME, columns, null, null, null, null, "NUMBER_OF_TIMES_ACCESSED DESC", "30");
		
		while(cursor.moveToNext()) {
			GradesheetMeta meta = new GradesheetMeta();
			meta.setLastAccessed(cursor.getString(2));
			meta.setNumberOfQuestions(cursor.getInt(0));
			meta.setNumberOfTimesAccessed(cursor.getInt(1));
			results.add(meta);
		}
				
		db.close();
		return results;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(GRADESHEET_HISTORY_TABLE_CREATE);

		int[] startingValues = new int[] {10, 20, 30, 40, 50, 15, 25, 35, 45, 55};

		for (int i = 0; i < startingValues.length; i++){
			ContentValues insertValues = new ContentValues();
			insertValues.put("NUMBER_OF_QUESTIONS", startingValues[i]);
			insertValues.put("NUMBER_OF_TIMES_ACCESSED", 0);
			insertValues.put("LAST_ACCESSED", 1);
			
			db.insert(GRADESHEET_HISTORY_TABLE_NAME, null, insertValues);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
