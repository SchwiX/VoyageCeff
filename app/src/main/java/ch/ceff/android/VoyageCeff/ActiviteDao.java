package ch.ceff.android.VoyageCeff;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ActiviteDao {

    @Query("SELECT * FROM tb_activites WHERE tb_activites.idDay = :dayId ORDER BY tb_activites.startHour, tb_activites.startMinute") // Selectionne seulement les activite du jour
    LiveData<List<Activite>> getAllActivitesFromIdDay(String dayId);

    @Query("SELECT tb_activites.* FROM tb_activites LEFT JOIN tb_dates ON tb_dates.id = tb_activites.idDay WHERE (tb_dates.dateDay = :dateDay AND tb_dates.dateMonth = :dateMonth AND tb_dates.dateYear = :dateYear) ORDER BY tb_activites.startHour, tb_activites.startMinute") // Selectionne seulement les activite du jour
    LiveData<List<Activite>> getAllActivitesFromToday(int dateDay, int dateMonth, int dateYear);

    @Insert
    void insert(Activite activite);

    @Delete
    void delete(Activite activite);
}
