package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.admin.ThemNhanVien;

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
    public void back(View view){
        finish();
    }
}