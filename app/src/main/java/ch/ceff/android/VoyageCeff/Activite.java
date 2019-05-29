package ch.ceff.android.VoyageCeff;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalTime;

public class Activite implements Parcelable {
    private int startHour, startMinute, endHour, endMinute;
    private String titreActivite;
    private String startTimeStr;
    private String endTimeStr;
    private LocalTime startTime;
    private LocalTime endTime;

    public Activite(int startHour, int startMinute, int endHour, int endMinute, String titreActivite) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.titreActivite = titreActivite;
        this.startTimeStr = String.format("%02d:%02d", startHour, startMinute);
        this.endTimeStr = String.format("%02d:%02d", endHour, endMinute);
        this.startTime = LocalTime.parse(startTimeStr);
        this.endTime = LocalTime.parse(endTimeStr);
    }

    protected Activite(Parcel in) {
        startHour = in.readInt();
        startMinute = in.readInt();
        endHour = in.readInt();
        endMinute = in.readInt();
        titreActivite = in.readString();
        startTimeStr = in.readString();
        endTimeStr = in.readString();
    }

    public static final Creator<Activite> CREATOR = new Creator<Activite>() {
        @Override
        public Activite createFromParcel(Parcel in) {
            return new Activite(in);
        }

        @Override
        public Activite[] newArray(int size) {
            return new Activite[size];
        }
    };

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getTitreActivite() {
        return titreActivite;
    }

    public void setTitreActivite(String titreActivite) {
        this.titreActivite = titreActivite;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return titreActivite + " de " + startTimeStr + " à " + endTimeStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(startHour);
        dest.writeInt(startMinute);
        dest.writeInt(endHour);
        dest.writeInt(endMinute);
        dest.writeString(titreActivite);
        dest.writeString(startTimeStr);
        dest.writeString(endTimeStr);
    }
}
