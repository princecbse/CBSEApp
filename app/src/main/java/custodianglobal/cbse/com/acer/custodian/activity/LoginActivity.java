package custodianglobal.cbse.com.acer.custodian.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import custodianglobal.cbse.com.acer.custodian.MainActivity;
import custodianglobal.cbse.com.acer.custodian.R;
import custodianglobal.cbse.com.acer.custodian.SettingActivity;
import custodianglobal.cbse.com.acer.custodian.utils.AppController;
import custodianglobal.cbse.com.acer.custodian.utils.Config;
import custodianglobal.cbse.com.acer.custodian.utils.PrefManager;

public class LoginActivity extends AppCompatActivity {
    String stOTP,stMobileNumber,stStream,stMobileNumbernext;
    private EditText editTextConfirmOtp,edMobileNumber;
    private AppCompatButton buttonRegister;
    private AppCompatButton buttonConfirm;
    TextView tvResndOtp;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    TextView tvTimer,heading1, link1,link2,link3;;
    ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    //String variables to hold username password and phone
    private String Selficode;
    private String password;
    private String phone;
    Button btnEnter;
    LinearLayout gov_layout,seting,main_li;
    ImageView img_fb,img_tw,img_yt,img_ig;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //checkAndRequestPermissions();
        prefManager=PrefManager.getInstance(this);
        seting=(LinearLayout) findViewById(R.id.seting);
        heading1=(TextView) findViewById(R.id.heading1);
        main_li=(LinearLayout) findViewById(R.id.main_li);
        gov_layout=(LinearLayout) findViewById(R.id.gov_link);
        img_fb=(ImageView) findViewById(R.id.img_fb);
        img_tw=(ImageView) findViewById(R.id.img_tw);
        img_yt=(ImageView) findViewById(R.id.img_yt);
        img_ig=(ImageView) findViewById(R.id.img_insta);
        link1=(TextView) findViewById(R.id.link1);
        edMobileNumber=findViewById(R.id.edAdminUsername);
        link2= findViewById(R.id.link2);
        link3=(TextView) findViewById(R.id.link3);
        requestQueue = Volley.newRequestQueue(this);
        btnEnter=(Button)findViewById(R.id.btnEnter);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SettingActivity.class));
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    if (validation1()) {
                        RegisterMobile();
                    } else {
                        Toast.makeText(LoginActivity.this, "Please fill required data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please check your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/cbseindia29"));
                startActivity(i);
            }
        });

        img_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://twitter.com/cbseindia29"));
                startActivity(i);
            }
        });

        img_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.instagram.com/cbse_hq_1929/"));
                startActivity(i);
            }
        });

        img_yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.youtube.com/channel/UCAre7caIM9EvmD-mcSy6VyA?view_as=subscriber"));
                startActivity(i);
            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.cbse.gov.in/cbsenew/documents//FAQ_CMTM-C.pdf"));
                startActivity(i);
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.cbse.gov.in/cbsenew/contact-us.html"));
                startActivity(i);
            }
        });

        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link3.getText().toString()));
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        settheam();
    }

    private void confirmOtp() throws JSONException {
        LayoutInflater li = LayoutInflater.from(this);
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);
        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
        tvResndOtp=(TextView)confirmDialog.findViewById(R.id.tvresendotp);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        tvTimer=(TextView)confirmDialog.findViewById(R.id.tvTimer);
        alert.setView(confirmDialog);
        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        //Displaying the alert dialog
        alertDialog.show();
        alertDialog.setCancelable(false);
        Button btncancel=(Button)confirmDialog.findViewById(R.id.btncnacel1);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    if (validation()) {
                        OTPMatch();
                    } else {
                        Toast.makeText(LoginActivity.this, "Please fill required data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please check your internet", Toast.LENGTH_SHORT).show();
                }
                tvResndOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            RegisterMobile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
    private boolean validation() {
        if (editTextConfirmOtp.getText().toString().equalsIgnoreCase("")) {
            editTextConfirmOtp.setError("Please enter recived code");
            editTextConfirmOtp.requestFocus();
            return false;
        }
        else {
            stOTP = this.editTextConfirmOtp.getText().toString().trim();
            return true;
        }
    }

    private boolean validation1() {
        if (edMobileNumber.getText().toString().equalsIgnoreCase("")) {
            edMobileNumber.setError("Please enter Mobile Number ");
            edMobileNumber.requestFocus();
            return false;
        }

        else {
            stMobileNumber = this.edMobileNumber.getText().toString().trim();
            return true;
        }
    }
    public void RegisterMobile() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                Config.REGISTER_Mobile_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            JSONObject jsonobject = jsonarray.getJSONObject(0);
                           // JSONObject myJson = new JSONObject(response);
                            String status = jsonobject.getString("msg");
                           Log.i("rny",","+status);
                            if (status.equals("success")) {
                                Toast.makeText(LoginActivity.this, "Mobile Number Register Successfully", Toast.LENGTH_SHORT).show();
                                confirmOtp();
                            } else {
                                Toast.makeText(LoginActivity.this, "Userid does not exists" + "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Userid does not exists" + "", Toast.LENGTH_SHORT).show();
                        }
                        Log.i("rny", response.toString() + "su");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("rny", error.toString() + "su");
                Toast.makeText(LoginActivity.this, "Someting worng", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID", stMobileNumber);
                params.put("DeviceID", "fghjkpogjrtuinbvfd");
                params.put("RoleID", "1");
                params.put("TokenID", "123456");
               // Log.i("rny",stuserNme+","+stodb+","+stPass);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


    public void OTPMatch() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                Config.Verify_OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            JSONObject jsonobject = jsonarray.getJSONObject(0);
                            String status = jsonobject.getString("msg");
                            // Log.i("rny",name+","+empCde+","+status);
                            if (status.equals("success")) {
                                stMobileNumbernext = jsonobject.getString("LoginID");
                                Selficode = jsonobject.getString("Selfie");
                                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                     it.putExtra("LoginID", stMobileNumbernext);
                                    it.putExtra("status", "0");
                                    startActivity(it);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "OTP does not exists" + "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "OTP does not exists" + "", Toast.LENGTH_SHORT).show();
                        }
                        Log.i("rny", response.toString() + "su");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("rny", error.toString() + "su");
                Toast.makeText(LoginActivity.this, "Your Network is very poor please try again.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID", stMobileNumber);
                params.put("OTP", stOTP);
                 Log.i("rny",stMobileNumber+"");
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("JNVCUST")
                        .setMessage("PERMISSION FOR LOCATION")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                       // locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    public void settheam (){
        String backcolor=prefManager.getString("backcolor");
        String fontsize=prefManager.getString("fontsize");
        switch (backcolor){
            case "Normal":
                main_li.setBackgroundColor(getResources().getColor(R.color.normal_color));
                break;
            case "Dark":
                main_li.setBackgroundColor(getResources().getColor(R.color.black));
                break;
            case " ":
                main_li.setBackgroundColor(getResources().getColor(R.color.normal_color));
                break;
        }
        switch (fontsize){
            case "Small":
              ;
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                break;
            case "Medium":

                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                //  heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                break;
            case "Large":

                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                // heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edMobileNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                break;
        }
    }

}
