package ch.ceff.android.VoyageCeff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Définir le composant ToolBar en tant que barre d'appli
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String couleur = preferences.getString("getCouleur", "#008577");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
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
