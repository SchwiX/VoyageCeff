package ch.ceff.android.VoyageCeff;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActiviteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private String dayId;

    // Database
    private ActiviteDao activiteDao;
    private AppDatabase appDatabase;
    private LiveData<List<Activite>> mAllActivites;
    private LiveData<List<Activite>> mAllActivitesToday;

    public ActiviteViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        activiteDao = appDatabase.activiteDao(); // Data access object
    }

    public void insert(Activite activite){
        new InsertAsyncTask(activiteDao).execute(activite);
    }

    public void delete(Activite activite)  {
        new DeleteAsyncTask(activiteDao).execute(activite);
    }

    LiveData<List<Activite>> getAllActivites(){
        this.mAllActivites = activiteDao.getAllActivitesFromIdDay(this.dayId); // Retourne toutes les activites du jour depuis la base
        Log.d(TAG, "Id du jour " + dayId);
        return mAllActivites;
    }

    LiveData<List<Activite>> getAllActivitesToday(){
        LocalDate date= LocalDate.now();
        int dateYear = date.getYear();
        Log.d(TAG, "dateYear " + dateYear);
        int dateMonth = date.getMonthValue();
        Log.d(TAG, "dateMonth " + dateMonth);
        int dateDay = date.getDayOfMonth();
        Log.d(TAG, "dateDay " + dateDay);
        this.mAllActivitesToday = activiteDao.getAllActivitesFromToday(dateDay, dateMonth, dateYear); // Retourne toutes les activites du jour depuis la base
        return mAllActivitesToday;
    }

    @Override
    protected void onCleared(){
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Activite, Void, Void> {
        ActiviteDao activiteDao;

        public InsertAsyncTask(ActiviteDao activiteDao){
            this.activiteDao = activiteDao;
        }

        @Override
        protected Void doInBackground(Activite... activites) {
            activiteDao.insert(activites[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Activite, Void, Void> {
        ActiviteDao activiteDao;

        public DeleteAsyncTask(ActiviteDao activiteDao){
            this.activiteDao = activiteDao;
        }

        @Override
        protected Void doInBackground(Activite... activites) {
            activiteDao.delete(activites[0]);
            return null;
        }
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }
}
