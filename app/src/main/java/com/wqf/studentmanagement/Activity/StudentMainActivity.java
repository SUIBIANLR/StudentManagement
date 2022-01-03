package com.wqf.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wqf.studentmanagement.Fragment.BasicInformationFragment;
import com.wqf.studentmanagement.Fragment.GradeFragment;
import com.wqf.studentmanagement.R;

public class StudentMainActivity extends AppCompatActivity {

    private static final String TAG = "StudentMainActivity";
    Button readGrade;
    Button readInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Log.d(TAG, "onCreate: 刚进入");
        readGrade = findViewById(R.id.read_grade);
        Log.d(TAG, "onCreate: readGrade按钮绑定完成");
        readInformation = findViewById(R.id.read_basic_information);
        Log.d(TAG, "onCreate: readInformation绑定完成");

        Bundle bundle = getIntent().getExtras();

        readGrade.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: 准备生成GradeFragment实例");
            GradeFragment gradeFragment = new GradeFragment();
            gradeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.student_main,gradeFragment).commit();
        });

        readInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate: 准备生成BasicInformationFragment实例");
                BasicInformationFragment basicInformationFragment = new BasicInformationFragment();
                basicInformationFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.student_main,basicInformationFragment).commit();
//                Log.d(TAG, "onCreate: 成功展示basicInformationFragment");

            }
        });
    }
}