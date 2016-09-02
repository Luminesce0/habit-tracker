package com.omegaspockatari.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by ${Michael} on 9/1/2016.
 */
public class HabitContract {

    /**
     * Inner class that defines constant values for the habits database table.
     */
    public static final class HabitEntry implements BaseColumns {

        /** Listed below are the keys for all columns of the habits table. */
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_DATE = "date";
        public final static String COLUMN_HABIT_TASK = "task";
        public final static String COLUMN_HABIT_COMPLETE = "complete";
        public final static String COLUMN_HABIT_PERSONAL_NOTES = "personal notes";

        /** Below are listed possibilities for complete */

        public final static int COMPLETE_UNKNOWN = 0;
        public final static int COMPLETE_TRUE = 1;
        public final static int COMPLETE_FALSE = 2;

    }
}
