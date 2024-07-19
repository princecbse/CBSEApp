package custodianglobal.cbse.com.acer.custodian.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import custodianglobal.cbse.com.acer.custodian.R;
public class ImageActivity extends AppCompatActivity {
  ImageView imgView;
    public static ProgressDialog progressDialog = null;
  Button btncacel,btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imgView=(ImageView)findViewById(R.id.imgView);
        btncacel=(Button)findViewById(
                R.id.btncancel);
        btncacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnok=(Button)findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("name");
        imgView.setImageBitmap(bitmap);
    }

}


