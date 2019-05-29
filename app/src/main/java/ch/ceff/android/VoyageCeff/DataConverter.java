package ch.ceff.android.VoyageCeff;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromLocalDate(LocalDateParceable localDateParceable) {
        if (localDateParceable == null) {
            return (null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public LocalDateParceable toLocalDate(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionValues>>() {
        }.getType();
        List<OptionValues> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    // TODO https://mobikul.com/add-typeconverters-room-database-android/
    // TODO https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9
}