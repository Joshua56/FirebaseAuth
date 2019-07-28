package www.joshmyapps.com.healthcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DefaulterActivity extends AppCompatActivity {

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);
        mLocationManager = new LocationManager(this);
        getLifecycle().addObserver(mLocationManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String lat = String.valueOf(mLocationManager.getLastLocation().get("latitude"));
        final String lon = String.valueOf(mLocationManager.getLastLocation().get("longitude"));
        Log.d(DefaulterActivity.class.getSimpleName(), lat);
        TextView vTextView = findViewById(R.id.location_text_view);
        vTextView.setText(String.format(Locale.getDefault(), "Longitude - %s \n\n Latitude - %s", lat, lon));
    }

    public void submitDefaulter(View view) {
    }
}
