package com.example.seolhalee.locationdifference;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{

    //DatabaseHelper myDB;
    private static final int LOCATION_REQUEST_CODE = 1;
    Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        invokeLocationManager();
    }

    private void invokeLocationManager() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissionRequested = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this); //permission process should be added later

        this.onLocationChanged(null); //just set the starting status cleanly
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeLocationManager();
            } else {
                Toast.makeText(this, "Unable to invoke location manager", Toast.LENGTH_LONG).show();
            }
        }
    }




    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onLocationChanged(Location location) { //called each time when location updated

        TextView speed = (TextView) findViewById(R.id.speed);
        TextView lon = (TextView) findViewById(R.id.longi);
        TextView lat = (TextView) findViewById(R.id.lati);
        TextView lastLon = (TextView) findViewById(R.id.lastLon);
        TextView lastLat = (TextView) findViewById(R.id.lastLat);

        if (location==null){
            speed.setText("-.- m/s");
            lon.setText("-");
            lat.setText("-");
        }
        else {

            float nCurrentSpeed = location.getSpeed(); //speed = location.getSpeed();
            double nCurrentLon = location.getLongitude();
            double nCurrentLat = location.getLatitude();
            double nCurrentTime = location.getTime();
            //boolean isInserted = myDB.insertData(nCurrentSpeed, nCurrentLon, nCurrentLat, nCurrentTime);
            speed.setText(nCurrentSpeed + " m/s");
            lon.setText(nCurrentLon + "");
            lat.setText(nCurrentLat + "");


            if (lastLocation != null) {
                lastLat.setText(lastLocation.getLatitude() + "");
                lastLon.setText(lastLocation.getLongitude() + "");


                if (location.distanceTo(lastLocation) < 0.5) {
                    Toast.makeText(this, "staionary", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "walking", Toast.LENGTH_SHORT).show();
                }


            }

            lastLocation = location;
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
