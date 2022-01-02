package com.wqf.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wqf.studentmanagement.DataBase.MyDBOpenHelper;
import com.wqf.studentmanagement.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "debug";
    EditText user;
    EditText password;
    RadioGroup radioGroup;
    RadioButton isTeacher, isStudent;
    Button login;
    Button register;
    Button test;

    long userID;
    String passWord = "";
    String identity = "";

    public static MyDBOpenHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBOpenHelper(MainActivity.this, "my.db", null, 1);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        radioGroup = findViewById(R.id.RadioGroup);
        isTeacher = findViewById(R.id.is_teacher);
        isStudent = findViewById(R.id.is_student);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        test = findViewById(R.id.test);

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == isTeacher.getId()) {
                identity = "教师";
            }
            if (i == isStudent.getId()) {
                identity = "学生";
            }
        });

        login.setOnClickListener(view -> {
            userID = Long.parseLong(user.getText().toString());
            Log.d(TAG, "onCreate: " + userID);
            passWord = password.getText().toString();

            if (!exist(userID, passWord))
                //显示提示：学号/工号或密码错误！
                Toast.makeText(MainActivity.this, "学号/工号或密码错误！", Toast.LENGTH_SHORT).show();
            else if (!exist(userID, passWord, identity))
                //显示提示：身份信息错误！
                Toast.makeText(MainActivity.this, "身份信息错误！", Toast.LENGTH_SHORT).show();
            else {
                //显示：登陆成功！
                Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //根据identity的值跳转到相应的页面，若identity为学生，还需向下一页面传送userID
                if (identity.equals("学生")) {
                    Intent intent = new Intent(this, StudentMainActivity.class);
                    intent.putExtra("studentNumber", userID);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, TeacherMainActivity.class);
                    startActivity(intent);
                }
            }
        });

        register.setOnClickListener(view -> {
            userID = Long.parseLong(user.getText().toString());
            passWord = password.getText().toString();

            if (exist(userID))
                //显示：该学号/工号已经存在，无需注册！
                Toast.makeText(MainActivity.this, "该学号/工号已经存在，无需注册！", Toast.LENGTH_SHORT).show();
            else {
                //注册
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                db.execSQL("insert into user(user_id,password,flag) values (?,?,?)", new Object[]{userID, passWord, identity});
                //检验一下是否注册成功
                if (exist(userID, passWord, identity))
                    Toast.makeText(MainActivity.this, "注册成功！您现在可以登陆了！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "未知原因注册失败！", Toast.LENGTH_SHORT).show();
            }
        });

        //测试的后门，直接登录3119005434学生，记得测试完删除！！！
        test.setOnClickListener(view -> {
            Intent intent = new Intent(this, StudentMainActivity.class);
            intent.putExtra("studentNumber", 3119005434L);
            startActivity(intent);
        });
    }

    private boolean exist(long userID) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where user_id = ?", new String[]{userID + ""});
        return cursor.moveToFirst();
    }

    private boolean exist(long userID, String passWord, String identity) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where user_id = ? and password = ? and flag = ?", new String[]{userID + "", passWord, identity});
        //如果指针指向结果集中第一个之后，发现有元组存在，那说明存在，否则不存在
        return cursor.moveToFirst();
    }


    private boolean exist(long userID, String passWord) {
        Cursor cursor;
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from user where user_id = ? and password = ?", new String[]{userID + "", passWord});
        //如果指针指向结果集中第一个之后，发现有元组存在，那说明存在，否则不存在
        return cursor.moveToFirst();
    }

}