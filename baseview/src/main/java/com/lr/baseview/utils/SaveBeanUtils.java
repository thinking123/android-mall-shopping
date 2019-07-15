package com.lr.baseview.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/5/2.
 */

public class SaveBeanUtils<T> {
    Context context;

    public SaveBeanUtils(Context context) {
        this.context = context;
    }
    /**
     * 保存
     * @param
     */
    public void saveObject(T userVo) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(userVo);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            DatabaseHelper dbhelper = DatabaseHelper.getInstens(context);
            SQLiteDatabase database = dbhelper.getWritableDatabase();
            database.execSQL("insert into classtable (classtabledata) values(?)", new Object[] { data });
            Log.e("=============","----------------保存");
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getObject() {
        T userVo = null;
        DatabaseHelper dbhelper = DatabaseHelper.getInstens(context);
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Log.e("=============","----------------获取");
        Cursor cursor = database.rawQuery("select * from classtable", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex("classtabledata"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    userVo = (T) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return userVo;
    }
}
