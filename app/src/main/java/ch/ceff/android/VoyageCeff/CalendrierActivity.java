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
    public static final int ADD_ACTIVITY_REQUEST = 3;
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
                // Ajoute l'activité
                addActivity(startHour, startMinute, endHour, endMinute, titreActivite);
            }
        }else if(requestCode == ADD_ACTIVITY_REQUEST){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Rendez-vous ajouté", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addDay(final Calendar calendar){
        final GridLayout gridLayout = new GridLayout(context); // Crée le grid layout
        actualCalendar = calendar; // Calendrier actuellement ouvert

        // Crée la text view à ajouter dans le scroll view
        TextView tv = new TextView(context, null, 0, R.style.scroll_view_day); // Text view principale
        tv.setText(String.format("%tD", calendar));

        tv.setOnClickListener(new View.OnClickListener() { // Quand on clic sur le jour
            @Override
            public void onClick(View v) {
                TextView tvInsideAdd = new TextView(context, null, 0, R.style.scroll_view_inside_add);

                tvInsideAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { // Ajoute une nouvelle heure avec activité
                        actualCalendar = calendar; // Calendrier actuellement ouvert
                        // Ouvre l'activité pour choisir l'heure de début et fin
                        Log.d(TAG, "Démarre l'activité TimePickerActivity pour choisir l'heure de début et fin de l'activité");
                        Intent intent = new Intent(context, TimePickerActivity.class);
                        startActivityForResult(intent, SELECT_TIME_REQUEST);
                    }
                });

                // Positionne la vue
                GridLayout.Spec row = GridLayout.spec(1);
                GridLayout.Spec column = GridLayout.spec(0);

                gridLayout.addView(tvInsideAdd, new GridLayout.LayoutParams(row, column)); // Ajoute la ligne pour ajouter des activités
            }
        });

        // Positionne la vue
        GridLayout.Spec row = GridLayout.spec(0);
        GridLayout.Spec column = GridLayout.spec(0);

        gridLayout.addView(tv, new GridLayout.LayoutParams(row, column));

        map.put(calendar, gridLayout); // Permet d'associer le jour au gridlayout

        refresh(); // Mets à jour l'affichage en ajoutant le jour au linear layout
    }

    // Ajoute une activité
    public void addActivity(int startHour, int startMinute, int endHour, int endMinute, String title){
        linearLayout.removeAllViews();
        for (Map.Entry<Calendar, GridLayout> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if(entry.getKey() == actualCalendar){ // On est dans le bon calendrier
                // Crée la text view à ajouter dans le scroll view
                TextView tv = new TextView(context, null, 0, R.style.scroll_view_inside);
                tv.setText(title + " de "+startHour+":"+startMinute+" à "+endHour+":"+endMinute);

                // Récupère tvInsideAdd
                TextView tvInsideAdd = (TextView)entry.getValue().getChildAt( entry.getValue().getChildCount() - 1);

                // Ajoute la vue dans le grid layout à la place de tvInsideAdd
                GridLayout.Spec row = GridLayout.spec(entry.getValue().getChildCount() - 1);
                GridLayout.Spec column = GridLayout.spec(0);
                entry.getValue().removeView(tvInsideAdd); // Supprime la vue actuelle pour pouvoir la remplacer
                entry.getValue().addView(tv, new GridLayout.LayoutParams(row, column)); // Ajoute la vue a la place de tvInsideAdd

                // Décale tvInsideAdd vers le bas
                row = GridLayout.spec(entry.getValue().getChildCount());
                column = GridLayout.spec(0);
                entry.getValue().addView(tvInsideAdd, new GridLayout.LayoutParams(row, column)); // Remet tvInsideAdd
            }
        }

        refresh();
    }

    public void refresh(){
        // Recrée entièrement l'affichage dans le linear layout
        linearLayout.removeAllViews();
        for (Map.Entry<Calendar, GridLayout> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            linearLayout.addView(entry.getValue());
        }
    }
}
