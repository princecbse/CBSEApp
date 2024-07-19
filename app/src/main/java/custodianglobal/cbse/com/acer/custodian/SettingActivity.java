package custodianglobal.cbse.com.acer.custodian;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import custodianglobal.cbse.com.acer.custodian.utils.PrefManager;

public class SettingActivity extends AppCompatActivity {

    PrefManager prefManager;
    ConstraintLayout main_li;
    LinearLayout seting;
    RadioButton normal,dark,small,medium,large;
    TextView heading1,heading2,link1,link2,link3;
    ImageView img_fb,img_tw,img_yt,img_ig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
       setlistner();
        settheam();
    }
    public void init(){
        prefManager=PrefManager.getInstance(this);
        normal=(RadioButton) findViewById(R.id.normal);
        dark=(RadioButton) findViewById(R.id.dark);
        small=(RadioButton) findViewById(R.id.small);
        medium=(RadioButton) findViewById(R.id.medium);
        large=(RadioButton) findViewById(R.id.large);
        main_li=(ConstraintLayout) findViewById(R.id.main_li);
        heading1=(TextView) findViewById(R.id.heading1);
        heading2=(TextView) findViewById(R.id.heading2);
        seting=(LinearLayout) findViewById(R.id.seting);
         img_fb=(ImageView) findViewById(R.id.img_fb);
         img_tw=(ImageView) findViewById(R.id.img_tw);
         img_yt=(ImageView) findViewById(R.id.img_yt);
         img_ig=(ImageView) findViewById(R.id.img_insta);
        link1=(TextView) findViewById(R.id.link1);
        link2=(TextView) findViewById(R.id.link2);
        link3=(TextView) findViewById(R.id.link3);


    }
    public void setlistner(){
        normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // normal.setChecked(true);
                    dark.setChecked(false);
                    prefManager.setString("backcolor",normal.getText().toString());
                    main_li.setBackgroundColor(getResources().getColor(R.color.white));
                    small.setTextColor(getResources().getColor(R.color.black));
                    medium.setTextColor(getResources().getColor(R.color.black));
                    large.setTextColor(getResources().getColor(R.color.black));
                    heading1.setTextColor(getResources().getColor(R.color.black));
                    heading2.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                     normal.setChecked(false);
                 //   dark.setChecked(false);
                    prefManager.setString("backcolor",dark.getText().toString());
                    main_li.setBackgroundColor(getResources().getColor(R.color.black));
                    small.setTextColor(getResources().getColor(R.color.white));
                    medium.setTextColor(getResources().getColor(R.color.white));
                    large.setTextColor(getResources().getColor(R.color.white));
                    heading1.setTextColor(getResources().getColor(R.color.white));
                    heading2.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
      small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
                  //small.setChecked(false);
                  medium.setChecked(false);
                  large.setChecked(false);
                  prefManager.setString("fontsize",small.getText().toString());
                  heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                  heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                  dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                  normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
              }
          }
      });
      medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
              //    medium.setChecked(false);
                  small.setChecked(false);
                  large.setChecked(false);
                  prefManager.setString("fontsize",medium.getText().toString());
                  heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                  heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                  dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                  normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

              }
          }
      });
      large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
                  medium.setChecked(false);
                  small.setChecked(false);
                 // large.setChecked(false);
                  prefManager.setString("fontsize",large.getText().toString());
                  heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                  heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                  dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                  normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
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
    public void settheam (){
        String backcolor=prefManager.getString("backcolor");
        String fontsize=prefManager.getString("fontsize");
        switch (backcolor){
            case "Normal":
                normal.setChecked(true);
                dark.setChecked(false);
                main_li.setBackgroundColor(getResources().getColor(R.color.white));
                small.setTextColor(getResources().getColor(R.color.black));
                medium.setTextColor(getResources().getColor(R.color.black));
                large.setTextColor(getResources().getColor(R.color.black));
                heading1.setTextColor(getResources().getColor(R.color.black));
                heading2.setTextColor(getResources().getColor(R.color.black));
                break;
            case "Dark":
                normal.setChecked(false);
                dark.setChecked(true);
                main_li.setBackgroundColor(getResources().getColor(R.color.black));
                small.setTextColor(getResources().getColor(R.color.white));
                medium.setTextColor(getResources().getColor(R.color.white));
                large.setTextColor(getResources().getColor(R.color.white));
                heading1.setTextColor(getResources().getColor(R.color.white));
                heading2.setTextColor(getResources().getColor(R.color.white));
            break;
            case " ":
                normal.setChecked(true);
                dark.setChecked(false);
                heading1.setTextColor(getResources().getColor(R.color.black));
                heading2.setTextColor(getResources().getColor(R.color.black));
                small.setTextColor(getResources().getColor(R.color.black));
                medium.setTextColor(getResources().getColor(R.color.black));
                large.setTextColor(getResources().getColor(R.color.black));
                main_li.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
        switch (fontsize){
            case "Small":
                medium.setChecked(false);
                small.setChecked(true);
                large.setChecked(false);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                break;
            case "Medium":
                medium.setChecked(true);
                small.setChecked(false);
                large.setChecked(false);
              //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
              //  heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._16sdp);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                break;
            case "Large":
                medium.setChecked(false);
                small.setChecked(false);
                large.setChecked(true);
              //  heading1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
               // heading2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,R.dimen._20sdp);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                   dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                break;
            case " ":
                medium.setChecked(false);
                small.setChecked(true);
                large.setChecked(false);
                heading1.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                heading2.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                dark.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                normal.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                break;
        }
        seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}