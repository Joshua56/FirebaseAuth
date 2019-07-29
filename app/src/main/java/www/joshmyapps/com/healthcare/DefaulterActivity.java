package www.joshmyapps.com.healthcare;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaulterActivity extends LocationActivity {

    private TextView mLatitudeTextView;
    private EditText mReasonEditText;
    private TextView mTrimesterTextView;
    private Spinner mDefaulterSpinner;
    private TextView mLongitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);

        mLatitudeTextView = findViewById(R.id.latitude_text_view);
        mLongitudeTextView = findViewById(R.id.longitude_text_view);

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
                put("latitude", latitude);
                put("longitude", longitude);
                put("trimester", mTrimesterTextView.getText().toString());
                put("reason", mReasonEditText.getText().toString());
            }
        };
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        mLatitudeTextView.setText(latitude);
        mLongitudeTextView.setText(longitude);
    }

}
