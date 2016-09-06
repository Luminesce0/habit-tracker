package com.omegaspockatari.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.omegaspockatari.habittracker.data.HabitContract.HabitEntry;
import com.omegaspockatari.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private static final String LOG_TAG = HabitActivity.class.getSimpleName();

    private HabitDbHelper habitDbHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        /**
         * Must be given context or it is a null object.
         */
        habitDbHelper = new HabitDbHelper(this);

        insertHabitInfo();
        mCursor = readHabitInfo();
        mCursor.close();
        updateHabitInfo();
        mCursor = readHabitInfo();
        mCursor.close();
        deleteHabitInfo();
        habitDbHelper.onDeleteDatabase(this, habitDbHelper);

    }


    /**
     * Data insertion for the database.
     */
    private void insertHabitInfo() {

        /**
         * Acquire data repository in write mode
         * Interestingly enough, this .getWritableDatabase comes from SQLiteOpenHelper.
         * Making progress understanding code! :)
         */
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_DATE, "Thursday, September 1960");
        values.put(HabitEntry.COLUMN_HABIT_TASK, "Vacuum");
        values.put(HabitEntry.COLUMN_HABIT_COMPLETE, HabitEntry.COMPLETE_FALSE);
        values.put(HabitEntry.COLUMN_HABIT_PERSONAL_NOTES, "Try doing a section of the house each day?");

        /**
         * Passing the SQLiteOpenHelper's getWritableDatabase's method's return into an SQLiteDatabase
         * allows for us to access these new functions. Important information to understand where the
         * actual methods are coming from
         *
         * This also takes a Content value that is read through ContentResolver which processes that
         * information. Comes from the interface of Parcelable which makes sense.
         *
         * This also returns the primary key value of the new row, which is only really useful for UI.
         * Even still, provides area to check for errors.
         */
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        if (newRowId >= 1) {
            Log.v(LOG_TAG, "Habit saved with id: " + newRowId);
        } else {
            Log.v(LOG_TAG, "Error saving habit: " + newRowId);
        }
    }

    /**
     * Would display information about the database on screen but currently only offers information
     * via Logs.
     */
    private Cursor readHabitInfo() {

        /**
         * Open a database to read from it, or create one if no readable database exists.
         */
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();

        /**
         * Projection String allows for the .query SQLiteDatabase method to know the rows necessary
         * to show.
         */
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_DATE,
                HabitEntry.COLUMN_HABIT_TASK,
                HabitEntry.COLUMN_HABIT_COMPLETE,
                HabitEntry.COLUMN_HABIT_PERSONAL_NOTES
        };

        /**
         * In a more serious version of code it would need to be invulnerable to SQL Injections.
         * This is capable by setting various parameters correctly with selection & selection args.
         * Not very sure about this so must study. Confusing.
         */
        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);

        /**
         * Basic display of information for logging - Database Header.
         */
        Log.v(LOG_TAG, "\n" + HabitEntry._ID
                + " - " + HabitEntry.COLUMN_HABIT_DATE
                + " - " + HabitEntry.COLUMN_HABIT_TASK
                + " - " + HabitEntry.COLUMN_HABIT_COMPLETE
                + " - " + HabitEntry.COLUMN_HABIT_PERSONAL_NOTES);

        /**
         * Indexing of each column - Will be used in a while loop when there is more information.
         */
        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DATE);
        int taskColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TASK);
        int completeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_COMPLETE);
        int personalNotesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_PERSONAL_NOTES);


        if (cursor.getCount() < 1) {
            /**
             * Do nothing.
             */
        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                Log.v(LOG_TAG, "i before cursor.move(i): " + i );
                cursor.moveToNext();

                int currentId = cursor.getInt(idColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentTask = cursor.getString(taskColumnIndex);
                int currentComplete = cursor.getInt(completeColumnIndex);
                String currentPersonalNote = cursor.getString(personalNotesColumnIndex);

                Log.v(LOG_TAG, "\n" + currentId + " - " + currentDate + " - " + currentTask + " - " +
                        currentComplete + " - " + currentPersonalNote);
            }
        }

        /**
         * Close cursor that is being read from. Releases resources and prevents memory leaks.
         *
         * No longer closed since method must return a cursor?
         */

        return cursor;

    }

    /**
     * Data updating
     */
    private void updateHabitInfo() {
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        /**
         * New Value for one column
         */
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_COMPLETE, HabitEntry.COMPLETE_TRUE);

        /**
         * Which row to update based on the title.
         */
        String selection = HabitEntry.COLUMN_HABIT_COMPLETE + " LIKE ? ";

        /**
         * http://stackoverflow.com/questions/17481981/using-int-value-in-selection-args-argument-of-sqlite-for-android
         */
        String[] selectionArgs = { String.valueOf(HabitEntry.COMPLETE_FALSE) };

        long newRowId = db.update(HabitEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        readHabitInfo();
    }

    /**
     * Data deletion
     */
    private void deleteHabitInfo() {
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();
        db.execSQL(habitDbHelper.SQL_DELETE_ENTRIES);
    }


    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     * <p>
     * Needn't be implemented currently because the project demands no UI.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }
}
