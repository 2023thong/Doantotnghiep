package gmail.com.qlcafepoly.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gmail.com.qlcafepoly.R;

public class Dangnhapnhanvien extends AppCompatActivity {
    TextView tvQuanly, tvNhanvien;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhapnhanvien);

        tvQuanly = findViewById(R.id.tvQuanly);
        tvQuanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dangnhapnhanvien.this, Danhnhap.class);
                startActivity(intent);
            }
        });
    }
}