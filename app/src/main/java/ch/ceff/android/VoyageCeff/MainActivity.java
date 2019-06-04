package ch.ceff.android.VoyageCeff;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActiviteViewModel actualActiviteViewModel; // Permet l'accès a la base de donnée

    private RecyclerView mRecyclerView;
    private ActiviteListAdapter mAdapter;
    private ArrayList<Activite> activiteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int texteCouleur = preferences.getInt("getTexteCouleur", R.style.LeNoir);
        setTheme(texteCouleur);
        /** **/

        setContentView(R.layout.activity_main);

        // Définir le composant ToolBar en tant que barre d'appli
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** PART 2 **/
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",R.color.blanc);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        /** **/

        setJour(); // Affiche le jour actuel

        activiteList = new ArrayList<>();

        //Initialisation le recyclerview
        mRecyclerView = findViewById(R.id.recyclerview);
        //Crée un objet ActiviteListAdapter avec la source de donnée
        mAdapter = new ActiviteListAdapter(this, activiteList);
        //Connexion du recyclerview qui est connecté avec l'adapter qui contient la liste de donnée
        mRecyclerView.setAdapter(mAdapter);
        //Définir un gestionnaire de layout par défaut pour RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Permet l'accès a la base de donnée
        actualActiviteViewModel = ViewModelProviders.of(this).get(ActiviteViewModel.class);
        actualActiviteViewModel.getAllActivitesToday().observe(this, new Observer<List<Activite>>() {
            @Override
            public void onChanged(@Nullable List<Activite> activiteList) {
                mAdapter.setActiviteList((ArrayList<Activite>) activiteList);
                Log.d(TAG, "Update la liste des activite du jour depuis la base de donnée");
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                //dayList.remove(position);
                actualActiviteViewModel.delete(mAdapter.getActiviteAtPosition(position));
                mAdapter.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
    }

    public void setJour() {
        DateFormat df = new SimpleDateFormat("d MMM", Locale.FRENCH);
        String date = df.format(Calendar.getInstance().getTime());
        TextView tv = (TextView) findViewById(R.id.tv_atm_date);
        tv.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Permet d'inflate le menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accueil:
                //voirAccueil();
                return true;
            case R.id.action_calendrier:
                voirCalendrier();
                return true;
            case R.id.action_check_list:
                voirCheckList();
                return true;
            case R.id.action_reglements:
                voirReglements();
                return true;
            case R.id.action_parametres:
                voirParametres();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void voirAccueil(){
        // TODO map avec la flèche
    }

    public void voirCalendrier(){
        Log.d(TAG, "Lancer CalendrierActivity");
        Intent intent = new Intent(this, CalendrierActivity.class);
        startActivity(intent);
    }

    public void voirCheckList(){
        Log.d(TAG, "Lancer Check List");
        Intent intent = new Intent(this, CheckListActivity.class);
        startActivity(intent);
    }

    public void voirReglements(){
        Log.d(TAG, "Lancer ReglementsActivity");
        Intent intent = new Intent(this, ReglementsActivity.class);
        startActivity(intent);
    }

    public void voirParametres(){
        Log.d(TAG, "Lancer ParametresActivity");
        Intent intent = new Intent(this, ParametresActivity.class);
        startActivity(intent);
    }

}
