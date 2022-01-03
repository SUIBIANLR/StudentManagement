package com.wqf.studentmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, "my.db", null, 1);
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(" +
                "user_id long primary key," +
                "password Varchar(50)," +
                "flag varchar(10))");

        db.execSQL("create table student_grade(" +
                "sno long," +
                "class varchar(20)," +
                "subject varchar(20)," +
                "score int," +
                "term int," +
                "note varchar(10)," +
                "primary key(sno,subject,term))");

        db.execSQL("create table student_basic_information(" +
                "sno long primary key," +
                "class varchar(20)," +
                "name varchar(20)," +
                "sex varchar(5)," +
                "birthday varchar(15)," +
                "native_place varchar(30)," +//籍贯
                "political_affiliation varchar(20)," +//政治面貌
                "id_number varchar(22)," +//身份证号
                "admission_date varchar(15)," +//入学年份
                "address varchar(50)," +//家庭住址
                "post_code varchar(10)," +//邮政编码
                "note varchar(10))");
    }

    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean isInTableUser(int userID){

        return false;
    }
}
