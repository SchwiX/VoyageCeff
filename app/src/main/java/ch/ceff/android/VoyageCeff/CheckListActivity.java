package ch.ceff.android.VoyageCeff;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class CheckListActivity extends AppCompatActivity {

    private static final String TAG = CheckListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //int texteCouleur = preferences.getInt("getTexteCouleur", R.style.AppTheme);
        int texteCouleur = preferences.getInt("getTexteCouleur", 0);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_check_list);
        /** **/
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        this.getWindow().getDecorView().setBackgroundResource(background);
        int background = preferences.getInt("getBackground",R.color.blanc);
        String couleur = preferences.getString("getCouleur", "#008577");
        /** PART 2 **/


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "I'm trying to add something");
                newBox("Test");
            }
        });
    }

    private void newBox(String s) {
        //TODO: Implement everything to use recycler view !
        RecyclerView rec = findViewById(R.id.check_list_recycler_view);
        CheckBox check = new CheckBox(this);
        check.setText(s);
        rec.addView(check);
        Log.d(TAG, "It's suppose to add now");

        /*Old code with linear :
        LinearLayout lay = findViewById(R.id.check_list_linear_layout);
        CheckBox check = new CheckBox(this);
        check.setText(s);
        lay.addView(check);
        Log.d(TAG, "It's suppose to add now");
         * This has placements issues
         */
    }

}
