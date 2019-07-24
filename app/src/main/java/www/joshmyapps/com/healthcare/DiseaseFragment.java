package www.joshmyapps.com.healthcare;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class DiseaseFragment extends Fragment {


    private TextView mTextview;

    public DiseaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_disease, container, false);
        mTextview = v.findViewById(R.id.disease_statement_text_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        String statement = String.format("%s for how many days?", Medication.disease);
        mTextview.setText(statement);
    }

    public void submitResponse(View view) {
        Toast.makeText(getContext(), "Submitted", Toast.LENGTH_LONG).show();
    }

}
