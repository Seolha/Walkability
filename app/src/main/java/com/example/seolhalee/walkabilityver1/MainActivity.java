package com.example.seolhalee.walkabilityver1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    DatabaseHelper myDB;
    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        invokeLocationManager();



    }


    private void invokeLocationManager() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissionRequested = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
        TextView speed = (TextView) this.findViewById(R.id.speed);
        TextView lon = (TextView) this.findViewById(R.id.longi);
        TextView lat = (TextView) this.findViewById(R.id.lati);

        if (location==null){
            speed.setText("-.- m/s");
            lon.setText("-");
            lat.setText("-");
        }
        else{
            float nCurrentSpeed = location.getSpeed(); //speed = location.getSpeed();
            double nCurrentLon = location.getLongitude();
            double nCurrentLat = location.getLatitude();
            double nCurrentTime = location.getTime();
            boolean isInserted = myDB.insertData(nCurrentSpeed, nCurrentLon, nCurrentLat, nCurrentTime);



            speed.setText(nCurrentSpeed+ " m/s");
            lon.setText(nCurrentLon+"");
            lat.setText(nCurrentLat+"");





        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
