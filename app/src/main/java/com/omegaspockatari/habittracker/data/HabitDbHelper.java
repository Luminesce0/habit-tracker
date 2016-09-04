package com.omegaspockatari.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omegaspockatari.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by ${Michael} on 9/1/2016.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /**
     * Database version incrementation.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Database Name
     */
    private static final String DATABASE_NAME = "habit.db";

    /**
     * Database key to delete table entries.
     */
    public static final String SQL_DELETE_ENTRIES = "delete from " + HabitEntry.TABLE_NAME;

    /**
     * Constructs a new instance of {@link HabitDbHelper}
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /**
         * SQL code to instantiate a habit table when run.
         *
         * Added NOT NULL to all fields to ensure data integrity within the database tables.
         */
        final String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + "( "
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HabitEntry.COLUMN_HABIT_DATE + " TEXT DEFAULT NOT NULL \"Unknown Date\","
                + HabitEntry.COLUMN_HABIT_TASK + " TEXT DEFAULT NOT NULL \"Unknown Task\","
                + HabitEntry.COLUMN_HABIT_COMPLETE + " INTEGER NOT NULL DEFAULT 0,"
                + HabitEntry.COLUMN_HABIT_PERSONAL_NOTES + " TEXT DEFAULT NOT NULL \"No Notes\");";

        /**
         * Executes the given SQL code statement. In this case, it will be creating a new table for us
         * to use overridden methods with.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_HABIT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /** Only relevant should the database be upgraded */
    }

    /**
     * Is it necessary to throw in the db from the activity calling this so the database can be
     * correctly closed? It may already be closed correctly in other methods so this additional
     * HabitDbHelper parameter may be moot.
     *
     * Method deletes database and other auxillary files.
     *
     * @param context
     * @param db
     */
    public void onDeleteDatabase(Context context, HabitDbHelper db) {
        db.close();
        context.deleteDatabase(HabitEntry.TABLE_NAME);
    }



}
