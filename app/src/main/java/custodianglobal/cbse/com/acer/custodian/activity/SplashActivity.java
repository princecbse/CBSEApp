package custodianglobal.cbse.com.acer.custodian.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import custodianglobal.cbse.com.acer.custodian.R;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar mProgress;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            public void run() {
                doWork();
                Intent it = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        }).start();
    }
    private void doWork() {
        for (int progress = 0; progress < 100; progress += 10) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
