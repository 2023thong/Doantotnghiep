package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class ThemNhanVien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
    }
    public void backthemnv(View view){
        Intent intent = new Intent(ThemNhanVien.this, Quanlynv.class);
        startActivity(intent);
    }
    public void backra(View view){
        finish();
    }
}