package com.volcano.pranjal.volcano;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {

        //To set the name of the Table
        public static final String TABLE_NAME = Constants.USER_SAVE_LATER_TABLE_NAME;

        //To set the name of the columns you need to add in your table
        //name, id and salary of your employees

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NEWS_ID = "newsid";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}