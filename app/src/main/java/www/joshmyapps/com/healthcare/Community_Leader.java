package www.joshmyapps.com.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Community_Leader extends AppCompatActivity {

    private Intent diarrhoeaIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__leader);
        getSupportActionBar().setTitle("Diseases");
        diarrhoeaIntent = new Intent(Community_Leader.this,Medication.class);
    }

    public void diarrhoea(View view) {
        diarrhoeaIntent.putExtra("Disease","Diarrhoea");
        startActivity(diarrhoeaIntent);
    }

    public void fever(View view) {
        diarrhoeaIntent.putExtra("Disease","Fever");
        startActivity(diarrhoeaIntent);
    }

    public void cough(View view) {
        diarrhoeaIntent.putExtra("Disease","Cough");
        startActivity(diarrhoeaIntent);
    }
}
