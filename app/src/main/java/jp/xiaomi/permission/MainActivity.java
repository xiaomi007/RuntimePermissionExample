package jp.xiaomi.permission;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    public static final int NOT_FIRST_TIME = 1;
    public static final int FIRST_TIME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissions");
        if (requestCode == NOT_FIRST_TIME) {
            Log.d(TAG, "Not first time");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission Granted");
                checkForLocation();
            } else {
                Log.d(TAG, "Permission Denied");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.ACCESS_FINE_LOCATION)) {
                    //Not first time
                    Log.d(TAG, "not first time");
                    ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, NOT_FIRST_TIME);
                } else {
                    //Always denied
                    Log.d(TAG, "always denied");
                    Toast.makeText(MainActivity.this, "Always Denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == FIRST_TIME) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission Granted");
            } else {
                Log.d(TAG, "Permission Denied first time");
                ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, NOT_FIRST_TIME);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void permission(View view) {
        Log.e(TAG, "onclick");
        if (PermissionChecker.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission granted");
            checkForLocation();
        } else {
            Log.d(TAG, "permission denied:" + PermissionChecker.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION));
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.ACCESS_FINE_LOCATION)) {
                //not first time
                Log.d(TAG, "not first time");
                ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, NOT_FIRST_TIME);
            } else {
                //first time or always denied
                Log.d(TAG, "first time or always denied");
                ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, FIRST_TIME);
            }
        }
    }

    private void checkForLocation() {
        Log.d(TAG, "checkForLocation: get a location");
    }
}
