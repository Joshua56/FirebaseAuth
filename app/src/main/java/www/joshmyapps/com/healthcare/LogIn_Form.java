package www.joshmyapps.com.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn_Form extends AppCompatActivity {


    Button button;
    EditText textEmail, textPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__form);
        getSupportActionBar().setTitle("Welcome to community Health");


        button = (Button) findViewById(R.id.buttonLogin);
        textPassword = (EditText) findViewById(R.id.text_password);
        textEmail = (EditText) findViewById(R.id.text_email);
        firebaseAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){

                    Toast toast =  Toast.makeText(LogIn_Form.this, "Please enter your email", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(password)){

                    Toast toast =  Toast.makeText(LogIn_Form.this, "Please enter your password", Toast.LENGTH_SHORT);
                    return;
                }
                if (password.length()>6) {

                    Toast toast = Toast.makeText(LogIn_Form.this, "Password too short..", Toast.LENGTH_SHORT);

                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                                Toast toast =  Toast.makeText(LogIn_Form.this, "Authentication Failed", Toast.LENGTH_SHORT);

                            }
                        });
                }
        });


    }



    public void Sign_in(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}
