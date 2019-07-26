package www.joshmyapps.com.healthcare;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By David Odari
 * On 25/07/19
 **/
public final class LocationManager implements LifecycleObserver {
    private FusedLocationProviderClient fusedLocationClient;
    private Context context;
    private double mLatitude;
    private double mLongitude;

    LocationManager(Context context) {
        this.context = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void initClient() {
        Log.d("Location", "Client Avaliable");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    Map<String, Double> getLastLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO Runtime Permissions
            Log.d(LocationManager.class.getSimpleName(), "No Permissions");
        }
        Log.d(LocationManager.class.getSimpleName(), "Proceed after Permissions");
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLatitude = location.getLatitude();
                            mLongitude = location.getLongitude();

                        } else {
                            //TODO Prompt to enable Location
                            Log.d(LocationManager.class.getSimpleName(), "Null");
                        }
                    }
                })
                .addOnFailureListener((Activity) context, new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LocationManager.class.getSimpleName(), e.toString());
                    }
                });
        Map<String, Double> location = new HashMap<>();
        location.put("longitude", mLongitude);
        location.put("latitude", mLatitude);
        return location;
    }

}

