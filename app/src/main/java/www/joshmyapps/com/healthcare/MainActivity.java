package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar vToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(vToolbar);
        /* Reference drawer layout */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        /* Add toggle and keep in sync */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                vToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(this);


    }

    public void community(View view) {
        startActivity(new Intent(getApplicationContext(), Community_Leader.class));
    }

    public void button_signUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp_Form.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_report_case)
            Toast.makeText(this, "Report Case", Toast.LENGTH_SHORT).show();
        else if (id == R.id.nav_defaulter_tracking)
            Toast.makeText(this, "Defaulter Tracking", Toast.LENGTH_SHORT).show();
        else if (id == R.id.nav_chat_room)
            Toast.makeText(this, "Chat Room", Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        /* Close drawer if its open */
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}
