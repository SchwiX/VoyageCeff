package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendrierActivity extends AppCompatActivity {

    private static final String TAG = CalendrierActivity.class.getSimpleName();
    //public static final String EXTRA_MESSAGE = "ch.ceff.android.VoyageCeff";
    public static final int ADD_DAY_REQUEST = 1;
    private FloatingActionButton fab;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        context = this;
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CalendrierAjouterJourActivity.class);
                Log.d(TAG, "Démarre l'activité CalendrierAjouterJourActivity pour ajouter un jour dans le calendrier");
                startActivityForResult(intent, ADD_DAY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_DAY_REQUEST) { // Identifie l'activité qui se termine
            if(resultCode == RESULT_OK) { // L'état final de l'activité
                Log.d(TAG, "L'activité ADD_DAY_REQUEST (CalendrierAjouterJourActivity) s'est terminée normalement.");
                int dateYear = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_YEAR, 2019);
                int dateMonth = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_MONTH, 1);
                int dateDay = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_DAY, 1);
                Calendar calendar = new GregorianCalendar(dateYear, dateMonth, dateDay);
                //Toast.makeText(this, String.format("%tD", calendar), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addDay(Calendar calendar){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30); // year month day hour min
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        startActivity(intent);
    }
}
