package custodianglobal.cbse.com.acer.custodian.utils;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by root on 6/2/18.
 */
public class MarshMallowPermission {
    public static final int ACCESS_COARE_LOCATION_CODE = 6;
    public static final int ACCESS_COARE_PhoneState = 11;
    public static final int CALL_PERMISSION_REQUEST_CODE = 4;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int GETACCOUNTPERMISSIONCODE = 7;
    public static final int READ_CONTACTS = 8;
    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int SMS_PERMISSION_REQUEST_CODE = 5;
    Activity activity;
    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }
    public boolean checkPermissionForRecord() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.RECORD_AUDIO") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForContact() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.READ_CONTACTS") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForSMS() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.READ_SMS") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForExternalStorage() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForFineLocation() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.ACCESS_FINE_LOCATION") == 12) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForCamera() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.CAMERA") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForCall() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.CALL_PHONE") == 0) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionPhoneState() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.READ_PHONE_STATE") == 11) {
            return true;
        }
        return false;
    }
    public boolean checkPermissionForAccount() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.GET_ACCOUNTS") == 0) {
            return true;
        }
        return false;
    }
    public void requestPermissionForSMS() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.READ_SMS")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_SMS"}, 5);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_SMS"}, 5);
    }
    public void requestPermissionForLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.ACCESS_COARSE_LOCATION")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 6);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 6);
    }
    public void requestPermissionForRecord() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.RECORD_AUDIO")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.RECORD_AUDIO"}, 1);
        }
    }
    public void requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
    }
    public void requestPermissionForContact() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.READ_CONTACTS")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_CONTACTS"}, 8);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_CONTACTS"}, 8);
    }
    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.CAMERA")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.CAMERA"}, 3);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.CAMERA"}, 3);
    }
    public void requestPermissionForCall() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.CALL_PHONE")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.CALL_PHONE"}, 3);
        }
    }
    public void requestPermissionForAcocount() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.GET_ACCOUNTS")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.GET_ACCOUNTS"}, 7);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.GET_ACCOUNTS"}, 7);
    }
    public void requestPermissionPhoneStaye() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.READ_PHONE_STATE")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_PHONE_STATE"}, 11);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.READ_PHONE_STATE"}, 11);
    }
    public void requestPermissionForFineLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, "android.permission.ACCESS_FINE_LOCATION")) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 11);
            return;
        }
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 11);
    }
}
