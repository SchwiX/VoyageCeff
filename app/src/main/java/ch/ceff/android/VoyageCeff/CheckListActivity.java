package ch.ceff.android.VoyageCeff;

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
        setContentView(R.layout.activity_check_list);

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
