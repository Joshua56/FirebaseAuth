package www.joshmyapps.com.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Medication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        Intent intent = getIntent();

        if(intent.hasExtra("Disease")){
        String disease = intent.getStringExtra("Disease");
        TextView textview = findViewById(R.id.count_days_text_view);
        textview.setText(disease);
        }
    }
}
