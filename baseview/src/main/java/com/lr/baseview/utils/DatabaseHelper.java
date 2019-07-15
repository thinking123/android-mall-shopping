package com.lr.baseview.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/2.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dbhelper = null;
    public static DatabaseHelper getInstens(Context context) {
        if (dbhelper == null) {
            dbhelper = new DatabaseHelper(context);
        }
        return dbhelper;
    }


    private DatabaseHelper(Context context) {
        super(context, "datebase.db", null, 1);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //这张表采用二进制文件存储对象注意第二个字段我们将对象存取在这里面
        String sql_class_table="create table if not exists classtable(_id integer primary key autoincrement,classtabledata text)";
        sqLiteDatabase.execSQL(sql_class_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
