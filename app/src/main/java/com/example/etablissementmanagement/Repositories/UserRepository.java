package com.example.etablissementmanagement.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.etablissementmanagement.DAO.UserDao;
import com.example.etablissementmanagement.DataBase.Database;
import com.example.etablissementmanagement.Models.User;
import com.example.etablissementmanagement.OnLoadCompleted;

import java.util.List;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        Database database = Database.getInstance(application);
        userDao = database.userDao();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void addUser(User user) {
        new addUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getUsers() {
        return userDao.getUsers();
    }


    private static class addUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private addUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }

    public static class getUserAsyncTask extends AsyncTask<User, Void, User> {

        private UserDao userDao;
        private User userTask;
        OnLoadCompleted callback;

        public getUserAsyncTask(UserDao userDao, OnLoadCompleted callback) {
            this.userDao = userDao;
            this.callback = callback;
        }

        @Override
        protected User doInBackground(User... users) {
            return userDao.getUser(users[0].getLogin(), users[0].getPassword());
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            userTask = user;
            this.callback.OnLoadCompleted();
        }

        public User getUser() {
            return userTask;
        }
    }


}
