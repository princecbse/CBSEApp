package custodianglobal.cbse.com.acer.custodian;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import custodianglobal.cbse.com.acer.custodian.utils.AppController;
import custodianglobal.cbse.com.acer.custodian.utils.Config;
import custodianglobal.cbse.com.acer.custodian.utils.GpsTracker;
import custodianglobal.cbse.com.acer.custodian.utils.MarshMallowPermission;
import custodianglobal.cbse.com.acer.custodian.utils.PrefManager;

public class SelfiActivity extends AppCompatActivity implements LocationListener {
    ImageView img;
    Button btnupload;
    String stdate,stclass,sttype;
    TextView tvupload,heading1;
    ProgressDialog progressDialog;
    EditText edname,eddesignation,edbranchcode;
    String stname,stdesignation,stbranchcode,StLoginId;
    String stLat,stLng,stImagePath;
    private GpsTracker gpsTracker;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    LinearLayout s1,seting;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfi_main);
        prefManager = new PrefManager(SelfiActivity.this);
        Bundle extras = getIntent().getExtras();
        s1=(LinearLayout) findViewById(R.id.s1) ;
        seting=(LinearLayout) findViewById(R.id.seting);
        heading1=(TextView) findViewById(R.id.heading1);
        StLoginId = extras.getString("LoginID");
        stdate = extras.getString("date");
        sttype = extras.getString("type");
        stclass = extras.getString("class");
        btnupload=(Button)findViewById(R.id.btnupload);
        tvupload=(TextView)findViewById(R.id.tvupload);
        tvupload.setText("Upload Selfie("+StLoginId+")");
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation())
                {
                    InsrtData();
                }
            }
        });
        edname=(EditText)findViewById(R.id.edname);
        eddesignation=(EditText)findViewById(R.id.eddesignation);
        edbranchcode=(EditText)findViewById(R.id.edbrnchcode);
        img=(ImageView)findViewById(R.id.selfi);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkPermission();
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SelfiActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    } else {
                        getLocation();
                    }
                }
                else
                {
                    getLocation();
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    }
                    else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
                else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        settheam();
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(SelfiActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Log.i("rny",latitude+","+longitude);
            stLat=latitude+"";
            stLng=longitude+"   ";
        }else{
            stLng="0";
            stLat="0";
            gpsTracker.showSettingsAlert();
        }
    }
    private void checkPermission() {
        MarshMallowPermission mallowPermission = new MarshMallowPermission(SelfiActivity.this);
        if (!mallowPermission.checkPermissionForCamera()) {
            mallowPermission.requestPermissionForCamera();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
       /* Log.i("rny","Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        stLat=location.getLatitude()+"";
        stLng=location.getLongitude()+"";*/
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

       /*  locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2))*/
        }catch(Exception e)
        {
        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(SelfiActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Bitmap thumbnail = null;
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (resultCode == RESULT_OK) {
                photo = (Bitmap) data.getExtras().get("data");
                ImageConvert(photo);
                img.setImageBitmap(photo);
            }
            //imageView.setImageBitmap(photo);
        }
    }
    public void ImageConvert( Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        stImagePath = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    private boolean validation() {
        final boolean isEmpty6 =stImagePath == null || stImagePath.trim().length() == 0;
        if (edname.getText().toString().equalsIgnoreCase("")) {
            edname.setError("Please Enter name");
            edname.requestFocus();
            return false;
        }
        else if (eddesignation.getText().toString().equalsIgnoreCase("")) {
            eddesignation.setError("Please Enter designation");
            eddesignation.requestFocus();
            return false;
        }
        else if (edbranchcode.getText().toString().equalsIgnoreCase("")) {
            edbranchcode.setError("Please Enter Branch/School code");
            edbranchcode.requestFocus();
            return false;
        }
        else if (isEmpty6) {
            Toast.makeText(SelfiActivity.this, "Please capture an image ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            stname = this.edname.getText().toString().trim();
            stdesignation = this.eddesignation.getText().toString().trim();
            stbranchcode = this.edbranchcode.getText().toString().trim();
            return true;
        }
    }

    public void InsrtData() {
        progressDialog = new ProgressDialog(SelfiActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                Config.Submit_selfie_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject myJson = new JSONObject(response);
                            String status = myJson.optString("msg");
                            Log.i("rny",","+status);
                            if (status.equals("Data saved successfully")) {
                                    Intent it = new Intent(SelfiActivity.this, MainActivity.class);
                                    it.putExtra("LoginID", StLoginId);
                                    it.putExtra("status", sttype);
                                    startActivity(it);
                                    finish();

                            } else {
                                new AlertDialog.Builder(SelfiActivity.this)
                                        .setTitle("CMTM-C")
                                        .setMessage(status)
                                        .setPositiveButton("ok", null)
                                        .show();
                                Toast.makeText(SelfiActivity.this, status+"", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Log.i("rny", response.toString() + "su");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("rny", error.toString() + "su");

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Mode", "RecordData");
                params.put("UserID", StLoginId);
                params.put("RoleID", "1");
                params.put("Na  me", stname);
                params.put("Ima`gePath", stImagePath);
                params.put("Designation", stdesignation);
                params.put("Code", stbranchcode);
                params.put("DeviceID", "121");
                params.put("TokenID", "111");
                params.put("SubmitDate", stdate);
                params.put("Class", stclass);
                params.put("TypeID", sttype);
                params.put("Lat", stLat);
                params.put("Long", stLng);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    public void settheam (){
        String backcolor=prefManager.getString("backcolor");
        String fontsize=prefManager.getString("fontsize");
        switch (backcolor){
            case "Normal":
                s1.setBackgroundColor(getResources().getColor(R.color.white));
                heading1.setTextColor(getResources().getColor(R.color.black));

                break;
            case "Dark":
                s1.setBackgroundColor(getResources().getColor(R.color.black));
                heading1.setTextColor(getResources().getColor(R.color.white));

                break;
            case " ":
                s1.setBackgroundColor(getResources().getColor(R.color.white));
                heading1.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        switch (fontsize){
            case "Small":


                edbranchcode.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                eddesignation.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edname.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                btnupload.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                break;
            case "Medium":
                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                //  heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                edbranchcode.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                eddesignation.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edname.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                btnupload.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                break;
            case "Large":

                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                // heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                edbranchcode.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                eddesignation.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edname.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                btnupload.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

                break;
            case " ":
                edbranchcode.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                eddesignation.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edname.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                btnupload.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                break;
        }
        seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelfiActivity.this,SettingActivity.class));
            }
        });
    }
}
