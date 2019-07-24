package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Medication extends AppCompatActivity {

    public static String disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        Intent intent = getIntent();

        if (intent.hasExtra("Disease")) {
            disease = intent.getStringExtra(getString(R.string.disease_key));
            Log.d(Medication.class.getSimpleName(), disease);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(disease);
        }
    }
}
