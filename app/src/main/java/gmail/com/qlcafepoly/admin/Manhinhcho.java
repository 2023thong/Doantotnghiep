package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.dangnhap.Danhnhap;

public class Manhinhcho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển đến SecondActivity
                Intent intent = new Intent(Manhinhcho.this, Danhnhap.class);
                startActivity(intent);

                finish();
            }
        }, 2000); // 10000 ms = 10 giây
    }

}