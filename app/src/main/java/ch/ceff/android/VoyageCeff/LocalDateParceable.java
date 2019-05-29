package ch.ceff.android.VoyageCeff;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class LocalDateParceable implements Parcelable {
    private int dateYear, dateMonth, dateDay;
    private LocalDate localDate;

    public LocalDateParceable(int dateYear, int dateMonth, int dateDay) {
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        localDate = LocalDate.of(dateYear, dateMonth, dateDay);
    }

    protected LocalDateParceable(Parcel in) {
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
