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

    @Insert
    void insert(Activite activite);

    @Delete
    void delete(Activite activite);
}
