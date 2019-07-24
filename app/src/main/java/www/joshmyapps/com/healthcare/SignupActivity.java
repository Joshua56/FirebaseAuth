package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    Button mButton;
    ProgressBar progressBar;
    EditText textEmail, textPassword, textConfirmPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Sign up");


        mButton = (Button) findViewById(R.id.button_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textPassword = (EditText) findViewById(R.id.text_password);
        textEmail = (EditText) findViewById(R.id.text_email);
        textConfirmPassword = (EditText) findViewById(R.id.textConfirm_password);


        firebaseAuth = FirebaseAuth.getInstance();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String confirmPassword = textConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(SignupActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(SignupActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {

                    Toast.makeText(SignupActivity.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() > 6) {

                    Toast.makeText(SignupActivity.this, "Password too short..", Toast.LENGTH_SHORT).show();


                }
                progressBar.setVisibility(View.VISIBLE);

                if (password.equals(confirmPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        Toast.makeText(SignupActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(SignupActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }

    public void button_signUpForm(View view) {
        startActivity(new Intent(getApplicationContext(), SignupActivity.class));
    }
}
