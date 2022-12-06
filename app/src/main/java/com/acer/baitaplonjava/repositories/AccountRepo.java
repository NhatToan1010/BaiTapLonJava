package com.acer.baitaplonjava.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.acer.baitaplonjava.database.AccountDAO;
import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.model.Account;

import java.util.List;

public class AccountRepo {
    private AccountDAO dao;
    private LiveData<List<Account>> mList;

    public AccountRepo(Application application) {
        this.dao = AppDatabase.getDatabase(application).accountDAO();
        mList = dao.findAll();
    }

    public LiveData<List<Account>> getAllList() {
        return mList;
    }
    public void insert(Account acc){
        new insertAsyncTask(dao).execute(acc);
    }

    public static class insertAsyncTask extends AsyncTask<Account, Void, Void>{
        private AccountDAO dao;

        public insertAsyncTask(AccountDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            dao.insert(accounts[0]);
            return null;
        }
    }

    public void update(Account acc){
        new updateAsyncTask(dao).execute(acc);
    }

    public static class updateAsyncTask extends AsyncTask<Account, Void, Void>{
        private AccountDAO dao;

        public updateAsyncTask(AccountDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            dao.update(accounts[0]);
            return null;
        }
    }

    public void delete(Account acc){
        new deleteAsyncTask(dao).execute(acc);
    }

    public static class deleteAsyncTask extends AsyncTask<Account, Void, Void>{
        private AccountDAO dao;

        public deleteAsyncTask(AccountDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            dao.delete(accounts[0]);
            return null;
        }
    }

    public void deleteAll(){
        dao.deleteAll();
    }

}
