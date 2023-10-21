package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class Quanlynv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlynv);
    }
    public void themnhanvien(View view){
        Intent intent = new Intent(Quanlynv.this, ThemNhanVien.class);
        startActivity(intent);
    }
    public void backNhanVien(View view){
        finish();
    }
}