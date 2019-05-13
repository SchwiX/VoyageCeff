package ch.ceff.android.VoyageCeff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerActivity extends AppCompatActivity {
    private static final String TAG = TimePickerActivity.class.getSimpleName();

    public static final String EXTRA_REPLY_START_HOUR = "ch.ceff.android.CalendrierActivity.extra.REPLY.startHour";
    public static final String EXTRA_REPLY_START_MINUTE = "ch.ceff.android.CalendrierActivity.extra.REPLY.startMinute";
    public static final String EXTRA_REPLY_END_HOUR = "ch.ceff.android.CalendrierActivity.extra.REPLY.endHour";
    public static final String EXTRA_REPLY_END_MINUTE = "ch.ceff.android.CalendrierActivity.extra.REPLY.endMinute";
    public static final String EXTRA_REPLY_TITRE_ACTIVITE = "ch.ceff.android.CalendrierActivity.extra.REPLY.titreActivite";

    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private EditText titreActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        // Ne sort pas du dialogue quand on clic en dehors
        this.setFinishOnTouchOutside(false);

        // On change le format des time picker en 24 h
        startTimePicker = findViewById(R.id.timePickerActivityStartTime);
        startTimePicker.setIs24HourView(true);

        endTimePicker = findViewById(R.id.timePickerActivityEndTime);
        endTimePicker.setIs24HourView(true);

        titreActivite = findViewById(R.id.titre_activite);
    }

    public void btnAddActivityPressed(View view) {
        String titre = titreActivite.getText().toString();
        if(!titre.equals("")){

            int startHour, startMinute;
            int endHour, endMinute;

            startHour = startTimePicker.getHour();
            startMinute = startTimePicker.getMinute();

            endHour = endTimePicker.getHour();
            endMinute = endTimePicker.getMinute();

            // Retourne l'heure de début et de fin
            Log.d(TAG, "TimePickerActivity retourne l'heure de début et de fin de l'activité");
            Intent replyIntent = new Intent(this, TimePickerActivity.class);

            replyIntent.putExtra(EXTRA_REPLY_START_HOUR, startHour);
            replyIntent.putExtra(EXTRA_REPLY_START_MINUTE, startMinute);
            replyIntent.putExtra(EXTRA_REPLY_END_HOUR, endHour);
            replyIntent.putExtra(EXTRA_REPLY_END_MINUTE, endMinute);
            replyIntent.putExtra(EXTRA_REPLY_TITRE_ACTIVITE, titre);

            setResult(RESULT_OK, replyIntent);
            finish();
        }else{
            Toast.makeText(this, "Merci de donner un titre à l'activité", Toast.LENGTH_SHORT).show();
        }
    }
}
