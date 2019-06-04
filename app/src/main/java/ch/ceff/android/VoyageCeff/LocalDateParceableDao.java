package ch.ceff.android.VoyageCeff;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocalDateParceableDao {

    @Query("SELECT * FROM tb_dates ORDER BY tb_dates.dateYear, tb_dates.dateMonth, tb_dates.dateDay")
    LiveData<List<LocalDateParceable>> getAllLocalDatesParceable();

    @Insert
    void insert(LocalDateParceable localDateParceables);

    @Delete
    void delete(LocalDateParceable localDateParceable);
}
