package com.wqf.studentmanagement.Fragment;

import static com.wqf.studentmanagement.Activity.MainActivity.myDBHelper;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wqf.studentmanagement.Activity.MainActivity;
import com.wqf.studentmanagement.DataStructure.Grade;
import com.wqf.studentmanagement.DataStructure.StudentInformation;
import com.wqf.studentmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "BasicInformationFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    long sno;
    StudentInformation studentInformation = new StudentInformation();

    EditText snoEditText;
    EditText birthday;
    EditText the_class;
    EditText native_place;
    EditText name;
    EditText political_affiliation;
    EditText sex;
    EditText admission_date;
    EditText post_code;
    EditText id_number;
    EditText address;
    EditText the_note;

    Button yes;

    public BasicInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicInformationFragment newInstance(String param1, String param2) {
        BasicInformationFragment fragment = new BasicInformationFragment();
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

        View theView = inflater.inflate(R.layout.fragment_basic_information, container, false);
        // Inflate the layout for this fragment

        Log.d(TAG, "onCreateView: 已进入onCreateView函数");
        //根据sno去数据库中寻找相应的数据
        sno=getArguments().getLong("studentNumber");
        studentInformation=getStudentInformation(sno);
        Log.d(TAG, "onCreateView: 已从数据库中读取到信息");
        snoEditText=theView.findViewById(R.id.sno);
        snoEditText.setText(sno+"");

        birthday=theView.findViewById(R.id.birthday);
        birthday.setText(studentInformation.getBirthday());

        the_class=theView.findViewById(R.id.the_class);
        the_class.setText(studentInformation.getTheClass());

        native_place=theView.findViewById(R.id.native_place);
        native_place.setText(studentInformation.getNativePlace());

        name=theView.findViewById(R.id.name);
        name.setText(studentInformation.getName());

        political_affiliation=theView.findViewById(R.id.political_affiliation);
        political_affiliation.setText(studentInformation.getPoliticalAffiliation());

        sex=theView.findViewById(R.id.sex);
        sex.setText(studentInformation.getSex());

        admission_date=theView.findViewById(R.id.admission_date);
        admission_date.setText(studentInformation.getAdmissionDate());

        post_code=theView.findViewById(R.id.post_code);
        post_code.setText(studentInformation.getPostCode());

        id_number=theView.findViewById(R.id.id_number);
        id_number.setText(studentInformation.getIdNumber());

        address=theView.findViewById(R.id.address);
        address.setText(studentInformation.getAddress());

        the_note=theView.findViewById(R.id.the_note);
        the_note.setText(studentInformation.getTheNote());

        yes=theView.findViewById(R.id.yes);
        yes.setOnClickListener(view -> {
            long sno = Long.parseLong(snoEditText.getText().toString());
            String theClass=the_class.getText().toString();
            String na=native_place.getText().toString();
            String nam=name.getText().toString();
            String p=political_affiliation.getText().toString();
            String s=sex.getText().toString();
            String a=admission_date.getText().toString();
            String post=post_code.getText().toString();
            String i=id_number.getText().toString();
            String ad=address.getText().toString();
            String theNote=the_note.getText().toString();
            String bir=birthday.getText().toString();
            //如果在数据库中没有这条记录，那么需要插入这一条记录，如果已存在，则需要更新
            if(exist(sno)){
                updateStudentInformation(sno,theClass,na,nam,p,s,a,post,i,ad,theNote,bir);
                Toast.makeText(getContext(), "好的！您的修改已完成！(如果您没有修改过个人信息请忽略本消息)", Toast.LENGTH_SHORT).show();
            } else{
                insertStudentInformation(sno,theClass,na,nam,p,s,a,post,i,ad,theNote,bir);
                Toast.makeText(getContext(), "您完善的信息已经提交！", Toast.LENGTH_SHORT).show();
            }
        });
        return theView;
    }

    private void insertStudentInformation(long sno, String theClass, String na, String nam, String p, String s, String a, String post, String i, String ad, String theNote, String bir) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("insert into student_basic_information(sno,class,name,sex,birthday,native_place,political_affiliation,id_number,admission_date,address,post_code,note)" +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{String.valueOf(sno),theClass,nam,s,bir,na,p,i,a,ad,post,theNote});
    }

    private boolean exist(long sno) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from student_basic_information WHERE sno=?", new String[]{String.valueOf(sno)});
        return cursor.moveToFirst();
    }

    private void updateStudentInformation(long sno, String theClass, String na, String nam, String p, String s, String a, String post, String i, String ad, String theNote, String bir) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("update student_basic_information set class = ?," +
                        "name=?," +
                        "sex=?," +
                        "birthday=?," +
                        "native_place=?," +
                        "political_affiliation=?," +
                        "id_number=?," +
                        "admission_date=?," +
                        "address=?," +
                        "post_code=?," +
                        "note=?" +
                        "where sno=?",
                new Object[]{theClass,nam,s,bir,na,p,i,a,ad,post,theNote,sno});
    }

    @SuppressLint("Range")
    private StudentInformation getStudentInformation(long sno) {
        StudentInformation studentInformation = new StudentInformation();
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student_basic_information where sno = ?", new String[]{sno+""});
        if (cursor.moveToFirst()){
            studentInformation.setSno(cursor.getLong(cursor.getColumnIndex("sno")));
            studentInformation.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            studentInformation.setTheClass(cursor.getString(cursor.getColumnIndex("class")));
            studentInformation.setNativePlace(cursor.getString(cursor.getColumnIndex("native_place")));
            studentInformation.setName(cursor.getString(cursor.getColumnIndex("name")));
            studentInformation.setPoliticalAffiliation(cursor.getString(cursor.getColumnIndex("political_affiliation")));
            studentInformation.setAdmissionDate(cursor.getString(cursor.getColumnIndex("admission_date")));
            studentInformation.setPostCode(cursor.getString(cursor.getColumnIndex("post_code")));
            studentInformation.setIdNumber(cursor.getString(cursor.getColumnIndex("id_number")));
            studentInformation.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            studentInformation.setTheNote(cursor.getString(cursor.getColumnIndex("note")));
            studentInformation.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            return studentInformation;
        }
        return studentInformation;
    }
}