package www.joshmyapps.com.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Community");

    }

    public void community(View view) {
        startActivity(new Intent(getApplicationContext(),Community_Leader.class));

    }

    public void button_signUp(View view) {

        startActivity(new Intent(getApplicationContext(),SignUp_Form.class));

    }
}
