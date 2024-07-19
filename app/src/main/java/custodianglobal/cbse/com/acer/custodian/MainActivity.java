
package custodianglobal.cbse.com.acer.custodian;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import net.igenius.customcheckbox.CustomCheckBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import custodianglobal.cbse.com.acer.custodian.activity.ImageActivity;
import custodianglobal.cbse.com.acer.custodian.utils.AppController;
import custodianglobal.cbse.com.acer.custodian.utils.Config;
import custodianglobal.cbse.com.acer.custodian.utils.GpsTracker;
import custodianglobal.cbse.com.acer.custodian.utils.MarshMallowPermission;
import custodianglobal.cbse.com.acer.custodian.utils.PrefManager;

public class MainActivity extends AppCompatActivity  {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private GpsTracker gpsTracker;
    String status1;
    String stidentity1,stidentity2,stidentity3,stidentity4,stidentity5,stidentity6;
    TextView tv1, tv2, tv3, tv4,tv5,tv6,tvmessage,l1,l2,l3,l4,l5,l6,txt_note;
    PrefManager prefManager;
    private static final int LONG_DELAY = 5500;
    String stseries1bagfrom,stseries1bagto,stseries2bagfrom,stseries2bagto;
    String stLat, stLng, stRemark, stImagePath, stnofArticale, stTypeId, rTypeId, stLoginID;
    EditText edNoofArtical, edremark,edseries1bagfrom,edseries1bagto,edseries2bagfrom,edseries2bagto;
    ProgressDialog progressDialog;
    CustomCheckBox scb1, scb2, scb3, scb4,scb5,scb6;
    private LocationListener locListener;
    LocationManager locManager;
    private Location mobileLocation;
    AlertDialog alertDialog;
    Button btn1,btn2,btn3,btn4,btn5,btn6;
    private String provider;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Button btnrecive, btncompilation, btnopen, btnhandover,btnhandover2,btnhandover3, btnEnter;
    LinearLayout back1,back,seting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // startAuthDialog();
        Bundle extras = getIntent().getExtras();
        stLoginID = extras.getString("LoginID");
        status1 = extras.getString("status");
        seting=(LinearLayout) findViewById(R.id.seting);
        getRecordData();
        l1=(TextView) findViewById(R.id.l1);
        l2=(TextView) findViewById(R.id.l2);
        l3=(TextView) findViewById(R.id.l3);
        l4=(TextView) findViewById(R.id.l4);
        l5=(TextView) findViewById(R.id.l5);
        l6=(TextView) findViewById(R.id.l6);
        txt_note=(TextView) findViewById(R.id.txt_note);
        back1=(LinearLayout) findViewById(R.id.back1);
        back=(LinearLayout) findViewById(R.id.back);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        edseries1bagfrom=(EditText)findViewById(R.id.series1bagfrom);
        edseries1bagto=(EditText)findViewById(R.id.series1bagto);
        edseries2bagfrom=(EditText)findViewById(R.id.series2bagfrom);
        edseries2bagto=(EditText)findViewById(R.id.series2bagto);
        tvmessage=(TextView)findViewById(R.id.tvmessage);
        prefManager = new PrefManager(MainActivity.this);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    if (validation()) {
                        InsrtData();
                    } else {
                        //Toast.makeText(MainActivity.this, "Please capture an image ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edNoofArtical = (EditText) findViewById(R.id.edNofofArtical);
        edremark = (EditText) findViewById(R.id.edRemark);
        scb1 = (CustomCheckBox) findViewById(R.id.scb1);
        scb2 = (CustomCheckBox) findViewById(R.id.scb2);
        scb3 = (CustomCheckBox) findViewById(R.id.scb3);
        scb4 = (CustomCheckBox) findViewById(R.id.scb4);
        scb5 = (CustomCheckBox) findViewById(R.id.scb5);
        scb6 = (CustomCheckBox) findViewById(R.id.scb6);

        scb1.setEnabled(false);
        scb2.setEnabled(false);
        scb5.setEnabled(false);
        scb4.setEnabled(false);
        scb3.setEnabled(false);
        scb6.setEnabled(false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkPermission();
        }
        btn1 =(Button)findViewById(R.id.btn1);
        btn2 =(Button)findViewById(R.id.btn2);
        btn3 =(Button)findViewById(R.id.btn3);
        btn4 =(Button)findViewById(R.id.btn4);
        btn5 =(Button)findViewById(R.id.btn5);
        btn6 =(Button)findViewById(R.id.btn6);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","1");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","2");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","3");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","4");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","5");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(MainActivity.this,SelfiActivity.class);
                it1.putExtra("LoginID",stLoginID);
                it1.putExtra("date","04/01/2019");
                it1.putExtra("type","6");
                it1.putExtra("class","10") ;
                startActivity(it1);
            }
        });

        btnrecive = (Button) findViewById(R.id.btnrecive);
        btncompilation = (Button) findViewById(R.id.btninspection);
        btnopen = (Button) findViewById(R.id.btnopen);
        btnhandover = (Button) findViewById(R.id.btnhandover);
        btnhandover2 = (Button) findViewById(R.id.btnhandover2);
        btnhandover3 = (Button) findViewById(R.id.btnhandover3);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        getLocation();
        btnrecive.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "1";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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

        btncompilation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "2";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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

        btnopen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "3";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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
        btnhandover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "4";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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
        btnhandover2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "5";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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

        btnhandover3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                stTypeId = "6";
                getLocation();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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

        btn1.setClickable(true);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);
        btn5.setClickable(false);
        btn6.setClickable(false);

        btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
        btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2))   ;
        btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));

        btnrecive.setClickable(true);
        btncompilation.setClickable(false);
        btnopen.setClickable(false);
        btnhandover.setClickable(false);
        btnhandover2.setClickable(false);
        btnhandover3.setClickable(false);

        btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
        btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
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
                Intent i = new Intent(this, ImageActivity.class);
                i.putExtra("name", photo);
                startActivity(i);
                if (stTypeId.equals("1")) {
                    tv1.setVisibility(View.VISIBLE);
                } else if (stTypeId.equals("2")) {
                    tv2.setVisibility(View.VISIBLE);
                } else if (stTypeId.equals("3")) {
                    tv3.setVisibility(View.VISIBLE);
                }
                else if (stTypeId.equals("4")) {
                    tv4.setVisibility(View.VISIBLE);
                }
                else if (stTypeId.equals("5")) {
                    tv5.setVisibility(View.VISIBLE);
                }
                else if (stTypeId.equals("6")) {
                    tv6.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void checkPermission() {
        MarshMallowPermission mallowPermission = new MarshMallowPermission(MainActivity.this);
        if (!mallowPermission.checkPermissionForCamera()) {
            mallowPermission.requestPermissionForCamera();
        }
    }
    private boolean validation() {
        final boolean isEmpty6 =stImagePath == null || stImagePath.trim().length() == 0;
        if (edNoofArtical.getText().toString().equalsIgnoreCase("")) {
            edNoofArtical.setError("Please Enter No of bags");
            edNoofArtical.requestFocus();
            return false;
        }
        else if (edseries1bagfrom.getText().toString().equalsIgnoreCase("")) {
            edseries1bagfrom.setError("Please Enter Series 1");
            edseries1bagfrom.requestFocus();
            return false;
        }
        /*else if (edseries1bagto.getText().toString().equalsIgnoreCase("")) {
            edseries1bagto.setError("Please Enter bag to");
            edseries1bagto.requestFocus();
            return false;
        }*/
        else if (edseries2bagfrom.getText().toString().equalsIgnoreCase("")) {
            edseries2bagfrom.setError("Please Enter Series 2");
            edseries2bagfrom.requestFocus();
            return false;
        }
        /*else if (edseries2bagto.getText().toString().equalsIgnoreCase("")) {
            edseries2bagto.setError("Please Enter bag to");
            edseries2bagto.requestFocus();
            return false;
        }*/
        else if (edremark.getText().toString().equalsIgnoreCase("")) {
            edremark.setError("Please Enter Remark");
            edremark.requestFocus();
            return false;
        }
        else if (isEmpty6) {
            Toast.makeText(MainActivity.this, "Please capture an image ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            stnofArticale = this.edNoofArtical.getText().toString().trim();
            stRemark = this.edremark.getText().toString().trim();
            stseries1bagfrom = this.edseries1bagfrom.getText().toString().trim();
           stseries1bagto = this.edseries1bagto.getText().toString().trim();
            stseries2bagfrom = this.edseries2bagfrom.getText().toString().trim();
           stseries2bagto = this.edseries2bagto.getText().toString().trim();
            return true;
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public void getRecordData() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait. we are geting information");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                Config.Submit_record_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            JSONObject myJson = jsonarray.getJSONObject(0);
                            String status = myJson.optString("msg");
                            stidentity1 = myJson.optString("TypeID1");
                            stidentity2 = myJson.optString("TypeID2");
                            stidentity3 = myJson.optString("TypeID3");
                            stidentity4 = myJson.optString("TypeID4");
                            stidentity5 = myJson.optString("TypeID5");
                            stidentity6=myJson.optString("TypeID6");
                            prefManager.setTypeId1(stidentity1);
                            prefManager.setTypeId2(stidentity2+"");
                            prefManager.setTypeId3(stidentity3+"");
                            prefManager.setTypeId4(stidentity4+"");
                            prefManager.setTypeId5(stidentity5+"");
                            prefManager.setTypeId6(stidentity6+"");
                            Log.i("rny", "," + status);
                            if (status.equals("success")) {
                                Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                                detils();
                            }
                            else {
                                Toast.makeText(MainActivity.this, status + "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                            progressDialog.dismiss();
                        }
                        Log.i("rny", response.toString() + "su");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("rny", error.toString() + "su");
                Toast.makeText(MainActivity.this, "Someting worng please try again", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Type", "RecordDetail");
                params.put("Value", "1");
                params.put("Description", stLoginID);

                Log.i("rny",  stLoginID + "");
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    public void ImageConvert(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        stImagePath = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }
    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Log.i("rny",latitude+","+longitude);
            stLat=latitude+"";
            stLng=longitude+"";
        }else{
            stLng="0";
            stLat="0";
            Toast.makeText(MainActivity.this, "We do not have latitude and longitude details.Please  allow location  access ", Toast.LENGTH_SHORT).show();
            gpsTracker.showSettingsAlert();
        }
    }
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/
    public void InsrtData() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                Config.Submit_Data_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject myJson = new JSONObject(response);
                            String status = myJson.optString("msg");
                            rTypeId = myJson.optString("TypeID");
                            Log.i("rny", "," + status);
                            if (status.equals("Data saved successfully")) {
                                status1.equals("0");
                                stImagePath="";
                                edNoofArtical.setText("");
                                edremark.setText("");
                                edseries1bagfrom.setText("");
                                edseries1bagto.setText("");
                                edseries2bagfrom.setText("");
                                edseries2bagto.setText("");
                                if (rTypeId.equals("1")) {
                                   // prefManager.setTypeId1(rTypeId + "");
                                    tv1.setVisibility(View.VISIBLE);
                                    scb1.setChecked(true);
                                  //  edNoofArtical.setHint("Enter total no of Completion  of confidential material");
                                    btnrecive.setClickable(false);
                                    btncompilation.setClickable(true);
                                    btnopen.setClickable(false);
                                    btnhandover.setClickable(false);
                                    btnhandover2.setClickable(false);
                                    btnhandover3.setClickable(false);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn1.setClickable(false);
                                    btn2.setClickable(true);
                                    btn3.setClickable(false);
                                    btn4.setClickable(false);
                                    btn5.setClickable(false);
                                    btn6.setClickable(false);
                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                } else if (rTypeId.equals("2")) {
                                   // prefManager.setTypeId2(rTypeId + "");
                                    scb2.setChecked(true);
                                  //  edNoofArtical.setHint("Enter total no of material inspected by representative");
                                    scb2.setChecked(true);
                                    tv2.setVisibility(View.VISIBLE);
                                    btnrecive.setClickable(false);
                                    btncompilation.setClickable(false);
                                    btnopen.setClickable(true);
                                    btnhandover.setClickable(false);
                                    btnhandover2.setClickable(false);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));;
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn1.setClickable(false);
                                    btn2.setClickable(false);
                                    btn4.setClickable(false);
                                    btn5.setClickable(false);
                                    btn6.setClickable(false);
                                    btn3.setClickable(true);
                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                } else if (rTypeId.equals("3")) {
                                  //  prefManager.setTypeId3(rTypeId + "");
                                    scb3.setChecked(true);
                                   // edNoofArtical.setHint("Enter total no of handing Over  material");
                                    scb3.setChecked(true);
                                    tv3.setVisibility(View.VISIBLE);
                                    btnrecive.setClickable(false);
                                    btncompilation.setClickable(false);
                                    btnopen.setClickable(false);
                                    btnhandover.setClickable(true);
                                    btnhandover2.setClickable(false);
                                    btnhandover3.setClickable(false);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn1.setClickable(false);
                                    btn2.setClickable(false);
                                    btn3.setClickable(false);
                                    btn4.setClickable(true);
                                    btn5.setClickable(false);
                                    btn6.setClickable(false);
                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    /*btnEnter.setClickable(false);
                                    tvmessage.setVisibility(View.VISIBLE);*/
                                    // edNoofArtical.setHint("Enter no of Hnaded article");
                                }
                                else if (rTypeId.equals("4")) {
                                    //  edNoofArtical.setHint("Enter total no of sealed boxes");
                                    scb4.setChecked(true);
                                    tv4.setVisibility(View.VISIBLE);
                                    btnrecive.setClickable(true);
                                    btncompilation.setClickable(false);
                                    btnopen.setClickable(false);
                                    btnhandover.setClickable(false);
                                    btnhandover2.setClickable(true);
                                    btnhandover3.setClickable(false);
                                    btn1.setClickable(false);
                                    btn2.setClickable(false);
                                    btn3.setClickable(false);
                                    btn4.setClickable(false);
                                    btn5.setClickable(true);
                                    btn6.setClickable(false);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));

                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                }
                                else if (rTypeId.equals("5")) {
                                    //  edNoofArtical.setHint("Enter total no of sealed boxes");
                                    scb5.setChecked(true);
                                    tv5.setVisibility(View.VISIBLE);
                                    btnrecive.setClickable(false);
                                    btncompilation.setClickable(false);
                                    btnopen.setClickable(false);
                                    btnhandover.setClickable(false);
                                    btnhandover2.setClickable(false);
                                    btnhandover3.setClickable(true);
                                    btn1.setClickable(false);
                                    btn2.setClickable(false);
                                    btn3.setClickable(false);
                                    btn4.setClickable(false);
                                    btn5.setClickable(false);
                                    btn6.setClickable(true);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));

                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
                                   /* btnEnter.setClickable(false);
                                    tvmessage.setVisibility(View.VISIBLE);*/
                                }
                                else if (rTypeId.equals("6")) {
                                    //  edNoofArtical.setHint("Enter total no of sealed boxes");
                                    scb6.setChecked(true);
                                    tv6.setVisibility(View.VISIBLE);
                                    btnrecive.setClickable(false);
                                    btncompilation.setClickable(false);
                                    btnopen.setClickable(false);
                                    btnhandover.setClickable(false);
                                    btnhandover2.setClickable(false);
                                    btnhandover3.setClickable(false);
                                    btn1.setClickable(false);
                                    btn2.setClickable(false);
                                    btn3.setClickable(false);
                                    btn4.setClickable(false);
                                    btn5.setClickable(false);
                                    btn6.setClickable(false);
                                    btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
                                    btnEnter.setClickable(false);
                                    tvmessage.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                            } else {

                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("CMTM-C")
                                        .setMessage(status)
                                        .setPositiveButton("ok", null)
                                        .show();
                                Toast.makeText(MainActivity.this, status + "", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
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
            params.put("UserID", stLoginID);
            params.put("TypeID", stTypeId);
            params.put("NoOfArtical", stnofArticale);
            params.put("ImagePath", stImagePath);
            params.put("Lat", stLat);
            params.put("Long", stLng);
            params.put("DeviceID", "1234567");
            params.put("TokenID", "HGGWDHQOPRSNVERSDFKOPIRI34KJCKEJGFIOURTIKFLEKROPTI34IWFELIWERT98");
            params.put("Remark", stRemark);
            params.put("RoleID", "1");
            params.put("NoOfTotalStud", "0");
            params.put("Series1From", stseries1bagfrom);
            params.put("Series1To", "100");
            params.put("Series2From", stseries2bagfrom);
            params.put("Series2To", "100");
            params.put("Class", "");
            params.put("SubmitedDate", "");
            params.put("NoOfAbsentStud", "0");
            params.put("NoOfPresentStud", "0");
            params.put("MappingUserId", "null");
            Log.i("rny", stLat + "" + stLng + ";" + stLoginID + "");
            return params;
        }
    };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                4000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    public void detils()
    {
        final boolean isEmpty1 = prefManager.getgetTypeId1() == null || prefManager.getgetTypeId1().trim().length() == 0;
        if (isEmpty1) {
        } else {
            tv1.setVisibility(View.VISIBLE);
            // prefManager.setTypeId1(rTypeId + "");
            scb1.setChecked(true);
            //  edNoofArtical.setHint("Enter total no of Completion  of confidential material");
            btnrecive.setClickable(false);
            btncompilation.setClickable(true);
            btnopen.setClickable(false);
            btnhandover.setClickable(false);
            btnhandover2.setClickable(false);
            btnhandover3.setClickable(false);
            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn1.setClickable(false);
            btn2.setClickable(true);
            btn3.setClickable(false);
            btn4.setClickable(false);
            btn5.setClickable(false);
            btn6.setClickable(false);
            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        }
        final boolean isEmpty2 = prefManager.getgetTypeId2() == null || prefManager.getgetTypeId2().trim().length() == 0;
        if (isEmpty2) {
        } else {
           // edNoofArtical.setHint("Enter total no of material inspected by representative");
            // prefManager.setTypeId2(rTypeId + "");
            scb2.setChecked(true);
            //  edNoofArtical.setHint("Enter total no of material inspected by representative");
            scb2.setChecked(true);
            tv2.setVisibility(View.VISIBLE);
            btnrecive.setClickable(false);
            btncompilation.setClickable(false);
            btnopen.setClickable(true);
            btnhandover.setClickable(false);
            btnhandover2.setClickable(false);
            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));;
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));

            btn1.setClickable(false);
            btn2.setClickable(false);
            btn4.setClickable(false);
            btn5.setClickable(false);
            btn6.setClickable(false);
            btn3.setClickable(true);
            
            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));

            //edNoofArtical.setHint("Enter no of Inspection  by JNV article");
        }
        final boolean isEmpty3 = prefManager.getgetTypeId3() == null || prefManager.getgetTypeId3().trim().length() == 0;
        if (isEmpty3) {
        } else {
            scb3.setChecked(true);
            // edNoofArtical.setHint("Enter total no of handing Over  material");
            scb3.setChecked(true);
            tv3.setVisibility(View.VISIBLE);
            btnrecive.setClickable(false);
            btncompilation.setClickable(false);
            btnopen.setClickable(false);
            btnhandover.setClickable(true);
            btnhandover2.setClickable(false);
            btnhandover3.setClickable(false);
            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));

            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(true);
            btn5.setClickable(false);
            btn6.setClickable(false);

            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
        }
        final boolean isEmpty4 = prefManager.getgetTypeId4() == null || prefManager.getgetTypeId4().trim().length() == 0;
        if (isEmpty4) {
        } else {
            scb4.setChecked(true);
            tv4.setVisibility(View.VISIBLE);
            btnrecive.setClickable(true);
            btncompilation.setClickable(false);
            btnopen.setClickable(false);
            btnhandover.setClickable(false);
            btnhandover2.setClickable(true);
            btnhandover3.setClickable(false);
            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(false);
            btn5.setClickable(true);
            btn6.setClickable(false);

            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));

            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            // edNoofArtical.setHint("Enter total no of sealed boxes");
        }
        final boolean isEmpty5 = prefManager.getgetTypeId5() == null || prefManager.getgetTypeId5().trim().length() == 0;
        if (isEmpty5) {
        } else {
            scb5.setChecked(true);
            tv5.setVisibility(View.VISIBLE);
            btnrecive.setClickable(false);
            btncompilation.setClickable(false);
            btnopen.setClickable(false);
            btnhandover.setClickable(false);
            btnhandover2.setClickable(false);
            btnhandover3.setClickable(true);
            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(false);
            btn5.setClickable(false);
            btn6.setClickable(true);

            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button));
            // edNoofArtical.setHint("Enter total no of sealed boxes");
        }

        final boolean isEmpty6 = prefManager.getgetTypeId5() == null || prefManager.getgetTypeId6().trim().length() == 0;
        if (isEmpty6) {
        } else {
            scb6.setChecked(true);
            tv6.setVisibility(View.VISIBLE);
            btnrecive.setClickable(false);
            btncompilation.setClickable(false);
            btnopen.setClickable(false);
            btnhandover.setClickable(false);
            btnhandover2.setClickable(false);
            btnhandover3.setClickable(false);
            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(false);
            btn5.setClickable(false);
            btn6.setClickable(false);

            btnrecive.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btncompilation.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnopen.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btnhandover3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));

            btnEnter.setClickable(false);
            tvmessage.setVisibility(View.VISIBLE);

        }
        if (status1.equals("1"))
        {
            btn1.setClickable(false);
            btn1.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod1");
        }
        else
        {}
        if(status1.equals("2"))
        {
            btn2.setClickable(false);
            btn2.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod2");
        }
        else
        {}
        if(status1.equals("3"))
        {
            btn3.setClickable(false);
            btn3.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod3");
        }
        else{}
        if(status1.equals("4"))
        {
            btn4.setClickable(false);
            btn4.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod3");
        }
        else{}
        if(status1.equals("5"))
        {
            btn5.setClickable(false);
            btn5.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod3");
        }
        else{}
        if(status1.equals("6"))
        {
            btn6.setClickable(false);
            btn6.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_gray_button2));
            Log.i("rny","callmethod3");
        }
        else{}
}
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    private void startAuthDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setLayout(800, 500);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);

        Button btn=(Button) dialogView.findViewById(R.id.btnok);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.getWindow().setContentView(dialogView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        settheam();
    }

    public void settheam (){
        String backcolor=prefManager.getString("backcolor");
        String fontsize=prefManager.getString("fontsize");
        switch (backcolor){
            case "Normal":
                back.setBackgroundColor(getResources().getColor(R.color.white));
                back1.setBackgroundColor(getResources().getColor(R.color.white));
                l1.setTextColor(getResources().getColor(R.color.black));
                l2.setTextColor(getResources().getColor(R.color.black));
                l3.setTextColor(getResources().getColor(R.color.black));
                l4.setTextColor(getResources().getColor(R.color.black));
                l5.setTextColor(getResources().getColor(R.color.black));
                l6.setTextColor(getResources().getColor(R.color.black));
                break;
            case "Dark":
                back.setBackgroundColor(getResources().getColor(R.color.black));
                back1.setBackgroundColor(getResources().getColor(R.color.black));
                l1.setTextColor(getResources().getColor(R.color.white));
                l2.setTextColor(getResources().getColor(R.color.white));
                l3.setTextColor(getResources().getColor(R.color.white));
                l4.setTextColor(getResources().getColor(R.color.white));
                l5.setTextColor(getResources().getColor(R.color.white));
                l6.setTextColor(getResources().getColor(R.color.white));
                break;
            case " ":
                back.setBackgroundColor(getResources().getColor(R.color.white));
                back1.setBackgroundColor(getResources().getColor(R.color.white));
                l1.setTextColor(getResources().getColor(R.color.black));
                l2.setTextColor(getResources().getColor(R.color.black));
                l3.setTextColor(getResources().getColor(R.color.black));
                l4.setTextColor(getResources().getColor(R.color.black));
                l5.setTextColor(getResources().getColor(R.color.black));
                l6.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        switch (fontsize){
            case "Small":

                l1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l3.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l4.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l5.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l6.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                txt_note.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edNoofArtical.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edremark.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries1bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries1bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries2bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries2bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);;
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                break;
            case "Medium":
                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                //  heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                l1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                l2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                l3.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                l4.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                l5.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                l6.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                txt_note.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edNoofArtical.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edremark.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edseries1bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edseries1bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edseries2bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                edseries2bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);;
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                break;
            case "Large":

                //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                // heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                l1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                l2.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                l3.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                l4.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                l5.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                l6.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                txt_note.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edNoofArtical.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edremark.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edseries1bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edseries1bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edseries2bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                edseries2bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                break;
            case " ":
                l1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l3.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l4.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l5.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                l6.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                txt_note.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edNoofArtical.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edremark.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries1bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries1bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries2bagfrom.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                edseries2bagto.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                btnEnter.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

                break;
        }
        seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });
    }
}