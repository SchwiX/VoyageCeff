package ch.ceff.android.VoyageCeff;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class LocalDateParceableViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    // Database
    private LocalDateParceableDao localDateParceableDao;
    private AppDatabase appDatabase;
    private LiveData<List<LocalDateParceable>> mAllLocalDatesParceable;

    public LocalDateParceableViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        localDateParceableDao = appDatabase.localDateParceableDao(); // Data access object
        mAllLocalDatesParceable = localDateParceableDao.getAllLocalDatesParceable();
    }

    public void insert(LocalDateParceable localDateParceable){
        new InsertAsyncTask(localDateParceableDao).execute(localDateParceable);
    }

    public void delete(LocalDateParceable localDateParceable)  {
        new DeleteAsyncTask(localDateParceableDao).execute(localDateParceable);
    }

    LiveData<List<LocalDateParceable>> getAllLocalDatesParceable(){
        return mAllLocalDatesParceable;
    }

    @Override
    protected void onCleared(){
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<LocalDateParceable, Void, Void>{
        LocalDateParceableDao localDateParceableDao;

        public InsertAsyncTask(LocalDateParceableDao localDateParceableDao){
            this.localDateParceableDao = localDateParceableDao;
        }

        @Override
        protected Void doInBackground(LocalDateParceable... localDateParceables) {
            localDateParceableDao.insert(localDateParceables[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<LocalDateParceable, Void, Void> {
        LocalDateParceableDao localDateParceableDao;

        public DeleteAsyncTask(LocalDateParceableDao localDateParceableDao){
            this.localDateParceableDao = localDateParceableDao;
        }

        @Override
        protected Void doInBackground(LocalDateParceable... localDateParceables) {
            localDateParceableDao.delete(localDateParceables[0]);
            return null;
        }
    }
}
