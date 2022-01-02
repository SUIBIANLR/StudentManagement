package com.wqf.studentmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wqf.studentmanagement.DataStructure.Grade;
import com.wqf.studentmanagement.R;

import java.util.List;

public class GradeAdapter extends ArrayAdapter<Grade> {
    private final int resourceId;

    public GradeAdapter(Context context, int textViewResourceId, List<Grade> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Grade grade = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView subject = view.findViewById(R.id.subject);
        TextView score = view.findViewById(R.id.score);
        TextView term = view.findViewById(R.id.term);
        TextView note = view.findViewById(R.id.note);

        subject.setText(grade.getSubject());
        score.setText(grade.getScore()+"");
        term.setText(grade.getTerm()+"");
        note.setText(grade.getNote());

        return view;
    }
}
