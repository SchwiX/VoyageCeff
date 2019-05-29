package ch.ceff.android.VoyageCeff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ParametresActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener */{

    private static final String TAG = ParametresActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int texteCouleur = preferences.getInt("getTexteCouleur", R.style.LeNoir);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_parametres);

        /** PART 2 **/
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",R.color.blanc);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        /** **/
    }

    String couleur;
    int background;
    int style;

    public void setColorMenu(String couleur){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getCouleur",couleur);
        editor.apply();
        Toast.makeText(this, "Paramètres sauvegardés !", Toast.LENGTH_SHORT).show();
    }

    public void setBackground(int background) {
        this.getWindow().getDecorView().setBackgroundResource(background);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("getBackground",background);
        editor.apply();
        Toast.makeText(this, "Paramètres sauvegardés !", Toast.LENGTH_SHORT).show();
    }

    public void setColorText(int style) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("getTexteCouleur",style);
        editor.apply();

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

        Toast.makeText(this, "Paramètres sauvegardés !", Toast.LENGTH_SHORT).show();
    }

    public void setColorPrimary(View view) {
        couleur = "#008577";
        setColorMenu(couleur);
    }

    public void setColorBleu(View view) {
        couleur = "#00008B";
        setColorMenu(couleur);
    }
    public void setColorOrange(View view) {
        couleur = "#FF8C00";
        setColorMenu(couleur);
    }

    public void setColorRouge(View view) {
        couleur = "#8B0000";
        setColorMenu(couleur);
    }

    public void setColorJaune(View view) {
        couleur = "#808000";
        setColorMenu(couleur);
    }

    public void setNoBackground(View view) {
        background = R.color.blanc;
        setBackground(background);
    }

    public void setBackground1(View view) {
        background = R.drawable.sun;
        setBackground(background);
    }

    public void setBackground2(View view) {
        background = R.drawable.moon;
        setBackground(background);
    }

    public void setBackground3(View view) {
        background = R.drawable.blood;
        setBackground(background);
    }

    public void setTextBlack(View view) {
        style = R.style.LeNoir;
        setColorText(style);
    }

    public void setTextWhite(View view) {
        style = R.style.LeBlanc;
        setColorText(style);
    }
}
