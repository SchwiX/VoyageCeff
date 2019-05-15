package ch.ceff.android.VoyageCeff;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CalendrierAjouterJourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier_ajouter_jour);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",0);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
    }
}
