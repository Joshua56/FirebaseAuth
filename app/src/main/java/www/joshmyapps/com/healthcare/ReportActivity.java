package www.joshmyapps.com.healthcare;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static www.joshmyapps.com.healthcare.ConstantsKt.GALLERY_RQ_CODE;

@RuntimePermissions
public class ReportActivity extends LocationActivity {

    private TextView mTvDays;
    private Spinner mCaseSpinner;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private ImageView mSelectedImageView;
    private TextView mImageText;
    private Uri mSelectedImage;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mCaseSpinner = findViewById(R.id.case_spinner);
        mTvDays = findViewById(R.id.days_text_view);
        mRadioGroup = findViewById(R.id.medication_radio_group);
        mSelectedImageView = findViewById(R.id.uploaded_image_view);
        mImageText = findViewById(R.id.image_placeholder_text);

        FirebaseStorage vFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = vFirebaseStorage.getReference().child("reports");

        FrameLayout vFrameLayout = findViewById(R.id.frame_layout);
        vFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportActivityPermissionsDispatcher.pickImageWithPermissionCheck(ReportActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == GALLERY_RQ_CODE) {
                mSelectedImage = data.getData();
                mSelectedImageView.setImageURI(mSelectedImage);
                mImageText.setVisibility(View.INVISIBLE);
            }
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        longitude = String.valueOf(location.getLongitude());
        latitude = String.valueOf(location.getLatitude());
    }

    public void reportCase(View view) {
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        if (!(selectedId == -1)) {
            mRadioButton = findViewById(selectedId);
        }
        if (mRadioButton != null) {
            String currentCase = mCaseSpinner.getSelectedItem().toString();
            String tookMeds = mRadioButton.getText().toString();
            String days = mTvDays.getText().toString();
            Map<String, String> report = new HashMap<>();
            report.put("case", currentCase);
            report.put("tookMeds", tookMeds);
            report.put("days", days);
            report.put("latitude", latitude != null ? latitude : "0.0");
            report.put("longitude", longitude != null ? longitude : "0.0");
            sendToFirebase(report);
            saveImageToFirebaseStorage(mSelectedImage, currentCase);
        }

    }

    private void saveImageToFirebaseStorage(Uri selectedImage, String rCase) {
        if (selectedImage != null) {
            final StorageReference mStoreRef = mStorageReference.child(rCase + selectedImage.getLastPathSegment());
            mStoreRef.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.d(ReportActivity.class.getSimpleName(), "Saving Unsuccessful");
                    }
                    return mStoreRef.getDownloadUrl();
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(ReportActivity.class.getSimpleName(), uri.toString());
                }
            });
        }
    }

    private void sendToFirebase(Map<String, String> report) {
        FirebaseUser vFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore vFirebaseFirestore = FirebaseFirestore.getInstance();
        if (vFirebaseUser != null) {
            vFirebaseFirestore.collection("reports")
                    .document(vFirebaseUser.getUid())
                    .collection(report.get("case"))
                    .document(new Timestamp(new Date().getTime()).toString())
                    .set(report)
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

    public void reduceDays(View view) {
        int days = Integer.parseInt(mTvDays.getText().toString());
        if (days > 0) {
            mTvDays.setText(String.valueOf(days - 1));
            return;
        }
        Toast.makeText(ReportActivity.this, "Invalid operation", Toast.LENGTH_LONG).show();
    }

    public void increaseDays(View view) {
        int days = Integer.parseInt(mTvDays.getText().toString()) + 1;
        mTvDays.setText(String.valueOf(days));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ReportActivityPermissionsDispatcher.onRequestPermissionsResult(ReportActivity.this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(intent, GALLERY_RQ_CODE);
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void onCameraDenied() {
        Toast.makeText(this, "No Permission to access media", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void onCameraNeverAskAgain() {
        Toast.makeText(this, "Enable permission from settings", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForCamera(PermissionRequest request) {
        showRationaleDialog(R.string.external_storage_rationale, request);
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
}
