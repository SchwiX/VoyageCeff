package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CalendrierActivity extends AppCompatActivity {

    private static final String TAG = CalendrierActivity.class.getSimpleName();
    //public static final String EXTRA_MESSAGE = "ch.ceff.android.VoyageCeff";
    public static final int ADD_DAY_REQUEST = 1;
    public static final int SELECT_TIME_REQUEST = 2;
    private FloatingActionButton fab;
    private Context context;
    private LinearLayout linearLayout;
    private Map<Calendar, GridLayout> map = new HashMap<>(); // Associe un calendar --> Jour au GridLayout --> Planning
    private Calendar actualCalendar; // Calendrier actuellement ouvert

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        context = this;
        linearLayout = findViewById(R.id.linear_layout_scroll_view);
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
                actualCalendar = calendar; // Calendrier actuellement ouvert
                addDay(calendar);
                Toast.makeText(this, String.format("%tD", calendar), Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == SELECT_TIME_REQUEST){
            if(resultCode == RESULT_OK){
                Log.d(TAG, "L'activité TimePickerActivity s'est terminée normalement.");
                int startHour = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_START_HOUR, 12);
                int startMinute = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_START_MINUTE, 30);
                int endHour = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_END_HOUR, 13);
                int endMinute = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_END_MINUTE, 30);
                String titreActivite = data.getStringExtra(TimePickerActivity.EXTRA_REPLY_TITRE_ACTIVITE);
                // TODO ajouter l'activité dans le calendrier
                        // on sauvegarde l'instance du calendar clique et on regarde dans la loop en fonction ....
            }
        }
    }

    public void addDay(Calendar calendar){
        final GridLayout gridLayout = new GridLayout(context); // Crée le grid layout

        // Crée la text view à ajouter dans le scroll view
        TextView tv = new TextView(context, null, 0, R.style.scroll_view_day); // Text view principale
        tv.setText(String.format("%tD", calendar));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvInsideAdd = new TextView(context, null, 0, R.style.scroll_view_inside_add);

                tvInsideAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { // Ajoute une nouvelle heure avec activité
                        // Ouvre l'activité pour choisir l'heure de début et fin
                        Log.d(TAG, "Démarre l'activité TimePickerActivity pour choisir l'heure de début et fin de l'activité");
                        Intent intent = new Intent(context, TimePickerActivity.class);
                        startActivityForResult(intent, SELECT_TIME_REQUEST);
                    }
                });

                GridLayout.Spec row = GridLayout.spec(1);
                GridLayout.Spec column = GridLayout.spec(0);

                gridLayout.addView(tvInsideAdd, new GridLayout.LayoutParams(row, column)); // Ajoute la ligne pour ajouter des activités
            }
        });

        GridLayout.Spec row = GridLayout.spec(0); // Première ligne
        GridLayout.Spec column = GridLayout.spec(0);

        gridLayout.addView(tv, new GridLayout.LayoutParams(row, column));

        map.put(calendar, gridLayout); // Permet d'associer le jour au gridlayout

        refresh(); // Mets à jour l'affichage en ajoutant le jour au linear layout
    }

    // Ajoute une activité
    public void addActivity(Calendar calendar, int startHour, int startMinute, int endHour, int endMinute, String title){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), startHour, startMinute); // year month day hour min
        Calendar endTime = Calendar.getInstance();
        endTime.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), endHour, endMinute);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Description")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Localisation")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "example@example.com");
        startActivity(intent);
    }

    public void refresh(){
        linearLayout.removeAllViews();
        for (Map.Entry<Calendar, GridLayout> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            linearLayout.addView(entry.getValue());
        }
    }
}
