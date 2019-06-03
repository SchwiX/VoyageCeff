package ch.ceff.android.VoyageCeff;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity(tableName = "tb_dates")
public class LocalDateParceable implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "dateYear")
    private int dateYear;

    @ColumnInfo(name = "dateMonth")
    private int dateMonth;

    @ColumnInfo(name = "dateDay")
    private int dateDay;

    @Ignore
    private LocalDate localDate;

    public LocalDateParceable(int dateYear, int dateMonth, int dateDay) {
        id = UUID.randomUUID().toString();
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        localDate = LocalDate.of(dateYear, dateMonth, dateDay);
    }

    protected LocalDateParceable(Parcel in) {
        id = in.readString();
        dateYear = in.readInt();
        dateMonth = in.readInt();
        dateDay = in.readInt();
        localDate = LocalDate.of(dateYear, dateMonth, dateDay);
    }

    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return "LocalDateParceable{" +
                "dateYear=" + dateYear +
                ", dateMonth=" + dateMonth +
                ", dateDay=" + dateDay +
                ", localDate=" + localDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(dateYear);
        dest.writeInt(dateMonth);
        dest.writeInt(dateDay);
    }

    public static final Creator<LocalDateParceable> CREATOR = new Creator<LocalDateParceable>() {
        @Override
        public LocalDateParceable createFromParcel(Parcel in) {
            return new LocalDateParceable(in);
        }

        @Override
        public LocalDateParceable[] newArray(int size) {
            return new LocalDateParceable[size];
        }
    };
}
