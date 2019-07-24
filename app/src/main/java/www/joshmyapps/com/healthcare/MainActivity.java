package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar vToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(vToolbar);
    }

    public void community(View view) {
        startActivity(new Intent(getApplicationContext(), CommunityActivity.class));
    }

    public void viewHealthWorker(View view) {
        startActivity(new Intent(this, HealthWorkerActivity.class));
    }
}
