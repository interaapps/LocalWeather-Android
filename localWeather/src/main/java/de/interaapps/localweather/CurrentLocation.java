package de.interaapps.localweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.interaapps.localweather.utils.LocationFailedEnum;
import de.interaapps.localweather.utils.NetworkUtil;

public class CurrentLocation {

    private WeakReference<Activity> activityWeakReference;
    private LocationCallback locationCallback = null;
    private FusedLocationProviderClient fusedLocationClient = null;
    private int requestCheckSettings = 2408;
    private int requestLocation = 2409;
    private boolean shouldWeRequestPermission;
    private boolean shouldWeRequestOptimization;
    private Callbacks callbacks;

    CurrentLocation(Activity activity,
                    boolean shouldWeRequestPermission,
                    boolean shouldWeRequestOptimization,
                    Callbacks callbacks) {

        this.shouldWeRequestPermission = shouldWeRequestPermission;
        this.shouldWeRequestOptimization = shouldWeRequestOptimization;
        this.callbacks = callbacks;
        activityWeakReference = new WeakReference<>(activity);

        init();
    }

    public interface Callbacks {
        void onSuccess(Location location);
        void onFailed(LocationFailedEnum locationFailedEnum);
    }

    @SuppressLint("MissingPermission")
    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activityWeakReference.get());
        Task task = fusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    callbacks.onSuccess(location);
                } else {
                    onLastLocationFailed();
                }
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onLastLocationFailed();
            }
        });
    }

    private void onLastLocationFailed() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    callbacks.onSuccess(locationResult.getLastLocation());
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
                return;
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if(!locationAvailability.isLocationAvailable()) {
                    callbacks.onFailed(LocationFailedEnum.HighPrecisionNA_TryAgainPreferablyWithInternet);
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
            }
        };

        if(activityWeakReference.get() == null) {
            return;
        }

        if(NetworkUtil.isInFlightMode(activityWeakReference.get())) {
            callbacks.onFailed(LocationFailedEnum.DeviceInFlightMode);
        } else {
            ArrayList<String> permissions = new ArrayList<>();
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissions.add(Manifest.permission.INTERNET);

            boolean permissionGranted = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activityWeakReference.get(), permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = false;
                    break;
                }
            }

            if (!permissionGranted) {
                if (shouldWeRequestPermission) {
                    String[] permissionArgs = permissions.toArray(new String[0]);
                    ActivityCompat.requestPermissions(activityWeakReference.get(), permissionArgs, requestLocation);
                } else {
                    callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted);
                }
            } else {
                getLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (activityWeakReference.get() == null) {
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(activityWeakReference.get())
                .checkLocationSettings((new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)).build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    if (activityWeakReference.get() == null) {
                        return;
                    }

                    try {
                        if (shouldWeRequestOptimization) {
                            ((ResolvableApiException) e).startResolutionForResult(activityWeakReference.get(), requestCheckSettings);
                        } else {
                            callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted);
                        }
                    } catch (IntentSender.SendIntentException ignored) { }
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (activityWeakReference.get() == null) {
            return;
        }

        if (requestCode == requestLocation) {
            if (grantResults.length == 0) {
                callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted);
                return;
            }

            boolean granted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                getLocation();
            } else {
                callbacks.onFailed(LocationFailedEnum.LocationPermissionNotGranted);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (activityWeakReference.get() == null) {
            return;
        }

        if (requestCode == requestCheckSettings) {
            if (resultCode == Activity.RESULT_OK) {
                getLocation();
            } else {
                LocationManager locationManager = (LocationManager) activityWeakReference.get().getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    callbacks.onFailed(LocationFailedEnum.HighPrecisionNA_TryAgainPreferablyWithInternet);
                } else {
                    callbacks.onFailed(LocationFailedEnum.LocationOptimizationPermissionNotGranted);
                }
            }
        }
    }
}
