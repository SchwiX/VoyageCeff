package ch.ceff.android.VoyageCeff;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ParametresActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener */{

    private static final String TAG = ParametresActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String couleur = preferences.getString("getCouleur", "#008577");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));


    }

    String couleur = "#008577";

    public void setColorPrimary(View view) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008577")));
        couleur = "#008577";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
    }

    public void setColorBleu(View view) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00008B")));
        couleur = "#00008B";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
    }

    public void setColorOrange(View view) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF8C00")));
        couleur = "#FF8C00";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
    }

    public void setColorRouge(View view) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8B0000")));
        couleur = "#8B0000";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
    }

    public void setColorJaune(View view) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#808000")));
        couleur = "#808000";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
    }

}
