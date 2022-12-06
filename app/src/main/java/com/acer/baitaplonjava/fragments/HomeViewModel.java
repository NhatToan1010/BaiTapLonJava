package com.acer.baitaplonjava.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.acer.baitaplonjava.model.Account;
import com.acer.baitaplonjava.model.Mark;
import com.acer.baitaplonjava.model.Student;
import com.acer.baitaplonjava.model.Subject;
import com.acer.baitaplonjava.repositories.AccountRepo;
import com.acer.baitaplonjava.repositories.MarkRepo;
import com.acer.baitaplonjava.repositories.StudentRepo;
import com.acer.baitaplonjava.repositories.SubjectRepo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<Student>> mStuList;
    private LiveData<List<Subject>> mSubList;
    private LiveData<List<Mark>> mMarkList;
    private LiveData<List<Account>> mAccountList;

    private StudentRepo mStuRepo;
    private SubjectRepo mSubRepo;
    private MarkRepo mMarkRepo;
    private AccountRepo mAccountRepo;

    public HomeViewModel(Application application) {
        super(application);
        this.mStuRepo = new StudentRepo(application);
        this.mSubRepo = new SubjectRepo(application);
        this.mMarkRepo = new MarkRepo(application);
        this.mAccountRepo = new AccountRepo(application);
        this.mStuList = mStuRepo.getAllList();
        this.mSubList = mSubRepo.getAllList();
        this.mMarkList = mMarkRepo.getAllList();
        this.mAccountList = mAccountRepo.getAllList();
    }

    public void insertStudent(Student stu){
        mStuRepo.insert(stu);
    }
    public void insertSubject(Subject sub){
        mSubRepo.insert(sub);
    }
    public void insertMark(Mark mark){
        mMarkRepo.insert(mark);
    }
    public void insertAccount(Account account){
        mAccountRepo.insert(account);
    }

    public void updateStudent(Student stu){
        mStuRepo.update(stu);
    }
    public void updateMark(Mark mark){
        mMarkRepo.update(mark);
    }

    public void deleteStudent(Student stu){
        mStuRepo.delete(stu);
    }

    public void deleteAllStudent(){
        mStuRepo.deleteAll();
    }
    public void deleteAllSubject(){
        mSubRepo.deleteAll();
    }
    public void deleteAllMark(){
        mMarkRepo.deleteAll();
    }

    public LiveData<List<Student>> getStudentList() {
        return mStuList;
    }

    public LiveData<List<Subject>> getSubjectList() {
        return mSubList;
    }

    public LiveData<List<Mark>> getMarkList() {
        return mMarkList;
    }

    public LiveData<List<Account>> getAccountList() {
        return mAccountList;
    }

    public List<Subject> getById(String id){
        return mSubRepo.getById(id);
    }
    public String findById(String id){
        return mSubRepo.findById(id);
    }
    public List<String> getSubId(){
        return mSubRepo.getId();
    }
    public List<String> getSubNameById(String id){
        return mSubRepo.getSubNameById(id);
    }

    public List<Float> getMaryById(String stuId){
        return mMarkRepo.getMarkById(stuId);
    }

    public int getCountOfMark(String stuId){
        return mMarkRepo.getCountOfMark(stuId);
    }
    public float getAVGMark(String stuId){
        return mMarkRepo.getAVGMark(stuId);
    }
}