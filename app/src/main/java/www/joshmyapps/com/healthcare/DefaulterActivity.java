package www.joshmyapps.com.healthcare;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private EditText mReasonEditText;
    private TextView mTrimesterTextView;
    private EditText mDefaulterNameEditText;
    private EditText mIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);

        mReasonEditText = findViewById(R.id.reason_edit_text);
        mTrimesterTextView = findViewById(R.id.days_text_view);
        mDefaulterNameEditText = findViewById(R.id.defaulter_edit_text);
        mIdEditText = findViewById(R.id.id_edit_text);

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
        FirebaseUser vFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore vFirebaseFirestore = FirebaseFirestore.getInstance();
        if (vFirebaseUser != null && mDefaulterNameEditText.getText() != null) {
            vFirebaseFirestore.collection("defaulters")
                    .document(vFirebaseUser.getUid())
                    .collection(mDefaulterNameEditText.getText().toString())
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
        } else {
            Toast.makeText(this, "Check Name", Toast.LENGTH_LONG).show();
        }
    }

    Map<String, String> defaulterData() {
        final String idText = mIdEditText.getText().toString();
        final String reasonText = mReasonEditText.getText().toString();
        return new HashMap<String, String>() {
            {
                put("latitude", latitude != null ? latitude : "0.0");
                put("longitude", longitude != null ? longitude : "0.0");
                put("trimester", mTrimesterTextView.getText().toString());
                put("reason", !reasonText.equals("") ? reasonText : "Not Provided");
                put("id", !idText.equals("") ? idText : "Not Provided");
            }
        };
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

}
