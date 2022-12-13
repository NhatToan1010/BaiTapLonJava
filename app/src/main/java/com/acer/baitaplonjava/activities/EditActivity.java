package com.acer.baitaplonjava.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.helpers.DateTimeHelper;
import com.acer.baitaplonjava.model.Student;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtID, edtName, edtMajor, edtAddress;
    private TextView edtDob;
    private RadioButton rBtnMale, rBtnFemale;
    private Button btnSave;
    private HomeViewModel mViewModel;
    private String date = "";
    private Date dob;
    private boolean isEdit = false;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("Edit");
        setSupportActionBar(toolbar);


        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtMajor = findViewById(R.id.edtMajor);
        edtAddress = findViewById(R.id.edtAddress);
        edtDob = (TextView) findViewById(R.id.edtDob);
        rBtnMale = findViewById(R.id.rBtnMale);
        rBtnFemale = findViewById(R.id.rBtnFemale);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        findViewById(R.id.btnClose).setOnClickListener(this);
        edtDob.setOnClickListener(this);

        getIntentStudent();
    }
    @SuppressLint("SetTextI18n")
    private void getIntentStudent(){
        if(getIntent().getExtras() != null){
            btnSave.setText("Update");
            Student stu = (Student) getIntent().getExtras().get("Student_key");
            edtID.setText(stu.getID());
            edtName.setText(stu.getName());
            edtMajor.setText(stu.getMajor());
            edtAddress.setText(stu.getAddress());
            date = DateTimeHelper.toString(stu.getDob());
            edtDob.setText(DateTimeHelper.toString(stu.getDob()));
            if (stu.isGender()){
                rBtnMale.setChecked(true);
            }else{
                rBtnFemale.setChecked(true);
            }
            isEdit = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edtDob:
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth+"/"+(1+month)+"/"+year;
                        edtDob.setText(date);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.setTitle("Date of Birth");
                datePickerDialog.show();
                break;
            case R.id.btnSave:
                String strID = edtID.getText().toString().trim();
                String strName = edtName.getText().toString().trim();
                String strMajor = edtMajor.getText().toString().trim();
                String strAddress = edtAddress.getText().toString().trim();
                try {
                    dob = DateTimeHelper.toDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean isMale = false;
                if (rBtnMale.isChecked()){
                    isMale = true;
                }
                Student stu = new Student(strID, strName, isMale, strMajor, strAddress, dob);

                if(TextUtils.isEmpty(strID) || TextUtils.isEmpty(strName)){
                    Toast.makeText(this, "Name and ID cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isEdit){
                    mViewModel.updateStudent(stu);
                    Toast.makeText(this, "Update successful!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    if (typeExit(stu)){
                        Toast.makeText(this, "Identical ID!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mViewModel.insertStudent(stu);
                    Toast.makeText(this, "Save successful!",
                            Toast.LENGTH_SHORT).show();
                }

                finish();
                break;
            case R.id.btnClose:
                finish();
                break;
        }
    }

    private boolean typeExit(Student stu){
        List<Student> mList = AppDatabase.getDatabase(this)
                .studentDAO().checkID(stu.getID());
        return mList != null && !mList.isEmpty();
    }

}
