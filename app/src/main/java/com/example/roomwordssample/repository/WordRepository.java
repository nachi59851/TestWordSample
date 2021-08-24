package com.example.roomwordssample.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roomwordssample.dao.WordDao;
import com.example.roomwordssample.database.WordRoomDatabase;
import com.example.roomwordssample.entity.Word;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {

        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords= mWordDao.getAllWords();

    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(Word mWord){
        new insertAsyncTask(mWordDao).execute(mWord);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void,Void,Void>{

        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao wordDao){
            mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteWord(Word word){
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao wordDao){
            mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.deleteWord(words[0]);
            return null;
        }
    }
}
