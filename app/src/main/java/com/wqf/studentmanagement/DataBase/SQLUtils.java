package com.wqf.studentmanagement.DataBase;

import static com.wqf.studentmanagement.Activity.MainActivity.myDBHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLUtils {
    private static final SQLiteDatabase db = myDBHelper.getWritableDatabase();

    //判断是否存在于数据库中user表中
    public static boolean isInTableUser(int userID) {
        //选出和p相同id的元组放入结果集中
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_id = ?",
                new String[]{String.valueOf(userID)});
        //若结果集为空，则说明不存在。反之则说明在表中
        if ((cursor.moveToFirst())) {
            return true;
        }
        return false;
    }
}
