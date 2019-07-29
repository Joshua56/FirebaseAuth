package www.joshmyapps.com.healthcare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaulterActivity extends AppCompatActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 001;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 002;
    private String mLat = "";
    private String mLon = "";
    private TextView mLocationTextView;
    private LocationManager mLocationManager;
    private EditText mReasonEditText;
    private TextView mTrimesterTextView;
    private Spinner mDefaulterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);

        mLocationTextView = findViewById(R.id.location_text_view);
        mReasonEditText = findViewById(R.id.reason_edit_text);
        mTrimesterTextView = findViewById(R.id.days_text_view);
        mDefaulterSpinner = findViewById(R.id.defaulter_spinner);

        ImageButton increaseButton = findViewById(R.id.inc_button);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int days = Integer.parseInt(mTrimesterTextView.getText().toString()) + 1;
                mTrimesterTextView.setText(String.valueOf(days));
            }
        });
        ImageButton decreaseButton = findViewById(R.id.dec_button);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int days = Integer.parseInt(mTrimesterTextView.getText().toString());
                if (days > 0) {
                    mTrimesterTextView.setText(String.valueOf(days - 1));
                    return;
                }
                Toast.makeText(DefaulterActivity.this, "Invalid operation", Toast.LENGTH_LONG).show();
            }
        });

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                } else {

                }
                return;
            }

        }
    }

    public void submitDefaulter(View view) {
        Log.d("DEF", "Clicked");
        FirebaseUser vFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore vFirebaseFirestore = FirebaseFirestore.getInstance();
        if (vFirebaseUser != null) {
            Log.d("DEF", "not null");
            vFirebaseFirestore.collection("defaulters")
                    .document(vFirebaseUser.getUid())
                    .collection(mDefaulterSpinner.getSelectedItem().toString())
                    .document(new Timestamp(new Date().getTime()).toString())
                    .set(defaulterData())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DefaulterActivity.class.getSimpleName(), "Success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DefaulterActivity.class.getSimpleName(), e.toString());
                        }
                    });
        }
    }

    Map<String, String> defaulterData() {
        return new HashMap<String, String>() {
            {
                put("latitude", mLat);
                put("longitude", mLon);
                put("trimester", mTrimesterTextView.getText().toString());
                put("reason", mReasonEditText.getText().toString());
            }
        };
    }

    @Override
    public void onLocationChanged(Location location) {
        String locationData = String.format("Longitude : %s \n\nLatitude : %s", location.getLongitude(), location.getLatitude());
        mLocationTextView.setText(locationData);
        mLat = String.valueOf(location.getLatitude());
        mLon = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
