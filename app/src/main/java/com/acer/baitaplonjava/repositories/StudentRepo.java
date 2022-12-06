package com.acer.baitaplonjava.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.database.StudentDAO;
import com.acer.baitaplonjava.model.Student;

import java.util.List;

public class StudentRepo {
    private StudentDAO dao;
    private LiveData<List<Student>> mList;

    public StudentRepo(Application application) {
        this.dao = AppDatabase.getDatabase(application).studentDAO();
        this.mList = dao.findAll();
    }

    public LiveData<List<Student>> getAllList() {
        return mList;
    }

    public void insert(Student stu){
        new insertAsyncTask(dao).execute(stu);
    }

    public static class insertAsyncTask extends AsyncTask<Student, Void, Void>{

        private StudentDAO dao;

        public insertAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.insert(students[0]);
            return null;
        }
    }

    public void update(Student stu){
        new updateAsyncTask(dao).execute(stu);
    }

    public static class updateAsyncTask extends AsyncTask<Student, Void, Void>{

        private StudentDAO dao;

        public updateAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.update(students[0]);
            return null;
        }
    }

    public void delete(Student stu){
        new deleteAsyncTask(dao).execute(stu);
    }

    public static class deleteAsyncTask extends AsyncTask<Student, Void, Void>{

        private StudentDAO dao;

        public deleteAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.delete(students[0]);
            return null;
        }
    }

    public void deleteAll(){
        dao.deleteAll();
    }
}
