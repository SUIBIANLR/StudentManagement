package com.wqf.studentmanagement.Fragment;

import static com.wqf.studentmanagement.Activity.MainActivity.myDBHelper;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wqf.studentmanagement.Activity.MainActivity;
import com.wqf.studentmanagement.Adapter.GradeAdapter;
import com.wqf.studentmanagement.DataStructure.Grade;
import com.wqf.studentmanagement.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.Subject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView gradesListView;

    List<Grade> grades = new ArrayList<>();
    long sno=3119005434L;

    public GradeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GradeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GradeFragment newInstance(String param1, String param2) {
        GradeFragment fragment = new GradeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //首先从数据库中获取该学生的成绩信息
        grades = getAllGrades(sno);

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_grade, container, false);
        gradesListView = theView.findViewById(R.id.grades_list_view);
        gradesListView.setAdapter(new GradeAdapter(getContext(),R.layout.grade_list_view_item, grades));
        gradesListView.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(getContext(), "您点击了第"+i+"行", Toast.LENGTH_SHORT).show());
        return theView;
    }

    private ArrayList<Grade> getAllGrades(long sno){
        ArrayList<Grade> grades = new ArrayList<>();
        Grade grade = null;
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student_grade where sno = ?", new String[]{sno+""});

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String subject = cursor.getString(cursor.getColumnIndex("subject"));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex("score"));
                @SuppressLint("Range") int term = cursor.getInt(cursor.getColumnIndex("term"));
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
                grade = new Grade(subject,score,term,note);
                grades.add(grade);
            }while (cursor.moveToNext());
            return grades;
        }
        return grades;
    }
}