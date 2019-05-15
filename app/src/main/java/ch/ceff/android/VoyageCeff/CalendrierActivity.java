package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CalendrierActivity extends AppCompatActivity {

    private static final String TAG = CalendrierActivity.class.getSimpleName();
    //public static final String EXTRA_MESSAGE = "ch.ceff.android.VoyageCeff";
    public static final int ADD_DAY_REQUEST = 1;
    public static final int SELECT_TIME_REQUEST = 2;
    private FloatingActionButton fab;
    private Context context;
    private LinearLayout linearLayout;
    private Map<LocalDate, GridLayout> map; // Associe une local date au gridlayout --> planning
    private LocalDate actualLocalDate; // LocalDate actuellement ouvert

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        context = this;
        map = new HashMap<>();  // Initialise la map
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
                LocalDate localDate = LocalDate.of(dateYear, dateMonth, dateDay); // Crée la date
                addDay(localDate);
                Toast.makeText(this, String.format("%tD", localDate), Toast.LENGTH_SHORT).show();
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
        }
    }

    public void addDay(final LocalDate localDate){
        final GridLayout gridLayout = new GridLayout(context); // Crée le grid layout
        // set la width et la height du gridlayout
        gridLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        actualLocalDate = localDate; // Jour actuellement ouvert

        // Crée la text view à ajouter dans le scroll view
        TextView tv = new TextView(context, null, 0, R.style.scroll_view_day); // Tout sauf attribut layout_

        // ------------------------
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(5, 5, 5, 0);
        tv.setLayoutParams(layoutParams);
        // ------------------------

        //tv.setText(String.format("%tD", localDate));
        tv.setText(localDate.format(DateTimeFormatter.ofPattern("d EEEE", Locale.FRANCE))); // format --> ex 21 Lundi

        tv.setOnClickListener(new View.OnClickListener() { // Quand on clic sur le jour
            @Override
            public void onClick(View v) {
                actualLocalDate = localDate; // localDate actuellement ouvert

                // On regarde si on a jamais ajouté tvInsideAdd
                GridLayout actualGirdLayout = getActualGridLayout();
                if(actualGirdLayout != null){
                    if(actualGirdLayout.getChildCount() < 2){ // Il y a seulement de jour dans le grid layout
                        TextView tvInsideAdd = new TextView(context, null, 0, R.style.scroll_view_inside_add);

                        // TODO regler bug layout params les margins le s'affichent pas ...
                        // ------------------------
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        layoutParams.setMargins(40, 0, 40, 0);
                        tvInsideAdd.setLayoutParams(layoutParams);
                        // ------------------------

                        // Positionne la vue
                        GridLayout.Spec row = GridLayout.spec(1);
                        GridLayout.Spec column = GridLayout.spec(0);

                        gridLayout.addView(tvInsideAdd, new GridLayout.LayoutParams(row, column)); // Ajoute la ligne pour ajouter des activités

                        tvInsideAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { // Ajoute une nouvelle heure avec activité
                                actualLocalDate = localDate; // localDate actuellement ouvert
                                // Ouvre l'activité pour choisir l'heure de début et fin
                                Log.d(TAG, "Démarre l'activité TimePickerActivity pour choisir l'heure de début et fin de l'activité");
                                Intent intent = new Intent(context, TimePickerActivity.class);
                                startActivityForResult(intent, SELECT_TIME_REQUEST);
                            }
                        });
                    }
                }
                refresh();
            }
        });

        // Positionne la vue
        GridLayout.Spec row = GridLayout.spec(0);
        GridLayout.Spec column = GridLayout.spec(0);

        gridLayout.addView(tv, new GridLayout.LayoutParams(row, column));

        map.put(localDate, gridLayout); // Permet d'associer le jour au gridlayout

        refresh(); // Mets à jour l'affichage en ajoutant le jour au linear layout
    }

    // Ajoute une activité
    public void addActivity(int startHour, int startMinute, int endHour, int endMinute, String title){
        // TODO Trier l'activité ajoutée par heure du jour
        linearLayout.removeAllViews();
        for (Map.Entry<LocalDate, GridLayout> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if(entry.getKey() == actualLocalDate){ // On est dans le bon localDate
                // Crée la text view à ajouter dans le scroll view
                TextView tv = new TextView(context, null, 0, R.style.scroll_view_inside);

                // ------------------------
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(40, 0, 40, 0);
                tv.setLayoutParams(layoutParams);
                // ------------------------


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

    // Trie la hashmap par LocalDate car elle implémente Comparable
    public Map<LocalDate, GridLayout> sortMapByLocalDate(){
        Map<LocalDate, GridLayout> sorted = new TreeMap<>();
        sorted.putAll(map); // Ajoute tout le contenu de la map
        return sorted;
    }

    public void refresh(){
        // Recrée entièrement l'affichage dans le linear layout
        linearLayout.removeAllViews();
        for (Map.Entry<LocalDate, GridLayout> entry : sortMapByLocalDate().entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if(entry.getKey() == actualLocalDate){ // Si c'est le jour actuellement ouvert --> affiche les activités
                // remet tous les enfants visibles
                for(int i=0; i<entry.getValue().getChildCount(); i++) {
                    TextView child = (TextView)entry.getValue().getChildAt(i);
                    child.setVisibility(View.VISIBLE);
                }
                linearLayout.addView(entry.getValue()); // visible
            }else{
                // met l'attibut visibility Gone a tous les enfants sauf le jour
                for(int i=1; i<entry.getValue().getChildCount(); i++) {
                    TextView child = (TextView)entry.getValue().getChildAt(i);
                    child.setVisibility(View.GONE); // GONE --> n'est pas inclu dans le rendu
                }
                linearLayout.addView(entry.getValue());
            }
        }
    }

    public GridLayout getActualGridLayout(){
        for (Map.Entry<LocalDate, GridLayout> entry : map.entrySet()) {
            if(entry.getKey() == actualLocalDate){ // Jour actuellement ouvert
                return entry.getValue();
            }
        }
        return null;
    }
}
