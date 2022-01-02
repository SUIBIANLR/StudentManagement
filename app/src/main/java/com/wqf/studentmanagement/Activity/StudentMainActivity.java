package com.wqf.studentmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wqf.studentmanagement.Fragment.GradeFragment;
import com.wqf.studentmanagement.R;

public class StudentMainActivity extends AppCompatActivity {

    Button readGrade;
    Button readInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        readGrade = findViewById(R.id.read_grade);
        readInformation = findViewById(R.id.read_basic_information);

        readGrade.setOnClickListener(view -> {
            GradeFragment gradeFragment = new GradeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.student_main,gradeFragment).commit();
        });
    }
}