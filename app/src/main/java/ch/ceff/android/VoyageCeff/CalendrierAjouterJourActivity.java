package ch.ceff.android.VoyageCeff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class CalendrierAjouterJourActivity extends AppCompatActivity {
    private static final String TAG = CalendrierAjouterJourActivity.class.getSimpleName();

    public static final String EXTRA_REPLY_YEAR = "ch.ceff.android.CalendrierActivity.extra.REPLY.Year";
    public static final String EXTRA_REPLY_MONTH = "ch.ceff.android.CalendrierActivity.extra.REPLY.Month";
    public static final String EXTRA_REPLY_DAY = "ch.ceff.android.CalendrierActivity.extra.REPLY.Day";

    private Button btnAddDay;
    private CalendarView calendarView;

    private int dateYear;
    private int dateMonth;
    private int dateDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier_ajouter_jour);
        btnAddDay = findViewById(R.id.btn_add_day);
        calendarView = findViewById(R.id.calendarView);
        // Listener quand la date change
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateMonth = month+1; // Car basé sur index 0
                dateYear = year;
                dateDay = dayOfMonth;
            }
        });
    }

    public void btnAddDayPressed(View view) {
        if(dateYear != 0) {
            Log.d(TAG, "Reponse à CalendrierActivity");
            Intent replyIntent = new Intent(this, CalendrierActivity.class);
            replyIntent.putExtra(EXTRA_REPLY_YEAR, dateYear);
            replyIntent.putExtra(EXTRA_REPLY_MONTH, dateMonth);
            replyIntent.putExtra(EXTRA_REPLY_DAY, dateDay);
            setResult(RESULT_OK, replyIntent);
            finish();
        }else{
            Toast.makeText(this, "Merci de sélectionner une date", Toast.LENGTH_SHORT).show();
        }
    }

}
