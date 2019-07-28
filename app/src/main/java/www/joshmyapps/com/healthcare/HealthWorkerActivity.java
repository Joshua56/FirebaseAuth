package www.joshmyapps.com.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HealthWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_worker);
        Toolbar vToolbar = findViewById(R.id.health_worker_toolbar);
        setSupportActionBar(vToolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        /* Close drawer if its open */
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_report_case)
            startActivity(new Intent(this, ReportActivity.class));
        else if (id == R.id.nav_defaulter_tracking)
            startActivity(new Intent(this, DefaulterActivity.class));
        else if (id == R.id.nav_chat_room)
            startActivity(new Intent(this, ChatRoomActivity.class));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
