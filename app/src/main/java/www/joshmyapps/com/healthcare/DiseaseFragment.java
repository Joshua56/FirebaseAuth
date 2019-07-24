package www.joshmyapps.com.healthcare;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class DiseaseFragment extends Fragment {


    private TextView mTextview;
    private TextView mDaysTextView;
    private RadioGroup mRadioGroup;
    private Button mSubmitButton;
    private View mViews;

    public DiseaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViews = inflater.inflate(R.layout.fragment_disease, container, false);
        mTextview = mViews.findViewById(R.id.disease_statement_text_view);
        mDaysTextView = mViews.findViewById(R.id.days_text_view);
        ImageButton increaseButton = mViews.findViewById(R.id.inc_button);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int days = Integer.parseInt(mDaysTextView.getText().toString()) + 1;
                mDaysTextView.setText(String.valueOf(days));
            }
        });
        ImageButton decreaseButton = mViews.findViewById(R.id.dec_button);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int days = Integer.parseInt(mDaysTextView.getText().toString());
                if (days > 0) {
                    mDaysTextView.setText(String.valueOf(days - 1));
                    return;
                }
                Toast.makeText(getActivity(), "Invalid operation", Toast.LENGTH_LONG).show();
            }
        });
        mRadioGroup = mViews.findViewById(R.id.medication_radio_group);
        mSubmitButton = mViews.findViewById(R.id.submit_button);
        return mViews;
    }

    @Override
    public void onResume() {
        super.onResume();
        String statement = String.format("%s for how many days?", MedicationActivity.disease);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            private RadioButton mRadioButton;

            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                if (!(selectedId == -1)) {
                    mRadioButton = mViews.findViewById(selectedId);
                }
                if (mRadioButton != null) {
                    String days = mDaysTextView.getText().toString();
                    Log.d("Crashing::", mRadioButton.getText().toString());
                    String tookMedication = mRadioButton.getText().toString();
                    saveToFirebase(MedicationActivity.disease, days, tookMedication);
                    Toast.makeText(getContext(), "Submitted", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Not Submitted", Toast.LENGTH_LONG).show();
            }
        });
        mTextview.setText(statement);
    }

    public void saveToFirebase(String type, String days, String tookMedication) {
        Disease vDisease = new Disease(type, days, tookMedication);
        FirebaseUser vFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore vFirebaseFirestore = FirebaseFirestore.getInstance();
        if (vFirebaseUser != null) {
            vFirebaseFirestore.collection("users")
                    .document(vFirebaseUser.getUid())
                    .collection("diseases")
                    .document(MedicationActivity.disease)
                    .set(parseDiseaseData(vDisease))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DiseaseFragment.class.getSimpleName(), "Success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DiseaseFragment.class.getSimpleName(), e.toString());
                        }
                    });
        }

    }

    private Map<String, String> parseDiseaseData(final Disease disease) {
        return new HashMap<String, String>() {
            {
                put("type", disease.getType());
                put("days", disease.getDays());
                put("medication", disease.tookMedication());
            }
        };
    }

}
