package ch.ceff.android.VoyageCeff;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocalDateParceableDao {
    @Query("SELECT * FROM tb_dates")
    List<LocalDateParceable> getAll();

    @Query("SELECT * FROM tb_dates where dateYear LIKE  :dateYear AND dateMonth LIKE :dateMonth AND dateDay LIKE :dateDay")
    LocalDateParceable findByName(int dateYear, int dateMonth, int dateDay);

    @Query("SELECT COUNT(*) from tb_dates")
    int countUsers();

    @Insert
    void insertAll(LocalDateParceable... localDateParceables);

    @Delete
    void delete(LocalDateParceable localDateParceable);
}
