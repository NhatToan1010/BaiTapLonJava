package com.acer.baitaplonjava.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.database.SubjectDAO;
import com.acer.baitaplonjava.model.Subject;

import java.util.List;

public class SubjectRepo {
    private SubjectDAO dao;
    private LiveData<List<Subject>> mList;

    public SubjectRepo(Application application) {
        this.dao = AppDatabase.getDatabase(application).subjectDAO();
        this.mList = dao.findAll();
    }

    public LiveData<List<Subject>> getAllList() {
        return mList;
    }
    public List<Subject> getById(String id){
        return dao.getByID(id);
    }
    public List<String> getId(){return dao.getID();}

    public String findById(String id){
        return dao.findById(id);
    }

    public void insert(Subject sub){
        new insertAsyncTask(dao).execute(sub);
    }

    public static class insertAsyncTask extends AsyncTask<Subject, Void, Void>{

        private SubjectDAO dao;

        public insertAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            dao.insert(subjects[0]);
            return null;
        }
    }

    public void update(Subject sub){
        new updateAsyncTask(dao).execute(sub);
    }

    public static class updateAsyncTask extends AsyncTask<Subject, Void, Void>{

        private SubjectDAO dao;

        public updateAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            dao.update(subjects[0]);
            return null;
        }
    }

    public void delete(Subject sub){
        new deleteAsyncTask(dao).execute(sub);
    }

    public static class deleteAsyncTask extends AsyncTask<Subject, Void, Void>{

        private SubjectDAO dao;

        public deleteAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            dao.delete(subjects[0]);
            return null;
        }
    }

    public void deleteAll(){
        dao.deleteAll();
    }
    public List<String> getSubNameById(String id){
        return dao.getSubNameById(id);
    }
}
