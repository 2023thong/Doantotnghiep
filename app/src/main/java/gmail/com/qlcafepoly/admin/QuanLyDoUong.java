package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class QuanLyDoUong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_do_uong);
    }
    public void tvCF(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangcoffee.class);
        startActivity(intent);
    }
    public void tvFruit(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangfruit.class);
        startActivity(intent);
    }
    public void tvTrs(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangmilktea.class);
        startActivity(intent);
    }
    public void tvNuocNgot(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangdouongkhac.class);
        startActivity(intent);
    }
    public void backDouong(View view){
        finish();
    }
}