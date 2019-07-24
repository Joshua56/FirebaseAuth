package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    private Intent diarrhoeaIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__leader);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Diseases");
        diarrhoeaIntent = new Intent(CommunityActivity.this, MedicationActivity.class);
    }

    public void diarrhoea(View view) {
        diarrhoeaIntent.putExtra("Disease", "Diarrhoea");
        startActivity(diarrhoeaIntent);
    }

    public void fever(View view) {
        diarrhoeaIntent.putExtra("Disease", "Fever");
        startActivity(diarrhoeaIntent);
    }

    public void cough(View view) {
        diarrhoeaIntent.putExtra("Disease", "Cough");
        startActivity(diarrhoeaIntent);
    }
}
