package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.ThemDoUongKhac;

public class Trangdouongkhac extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangdouongkhac);
    }
    public void backtrangdouongkhac(View view) {
        Intent intent = new Intent(Trangdouongkhac.this, QuanLyDoUong.class);
        startActivity(intent);
    }
    public void backcofee(View view){
        Intent intent = new Intent(Trangdouongkhac.this,QuanLyDoUong.class);
        startActivity(intent);
    }
    public void themdouong(View view){
        Intent intent = new Intent(Trangdouongkhac.this, ThemDoUongKhac.class);
        startActivity(intent);
    }
}