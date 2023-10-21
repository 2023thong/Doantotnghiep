package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gmail.com.qlcafepoly.R;

public class Thonhtintaikhoan extends AppCompatActivity {
    ImageView back1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thonhtintaikhoan);

        TextView tvManv = findViewById(R.id.tvManv1);
        TextView tvTennv = findViewById(R.id.tvTennv);
        TextView tvSdt = findViewById(R.id.tvSDt);
        TextView tvDiachi = findViewById(R.id.tvDiachi);
        TextView tvChucvu = findViewById(R.id.tvPhanquyen);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("thong", Context.MODE_PRIVATE);
        String Manv = sharedPreferences.getString("Manv", "");
        String Tennv = sharedPreferences.getString("TenNv", "");
        String Sdt = sharedPreferences.getString("Sdt", "");
        String Diachi = sharedPreferences.getString("Diachi", "");

        tvManv.setText(Manv);
        tvTennv.setText(Tennv);
        tvSdt.setText(Sdt);
        tvDiachi.setText(Diachi);
        tvChucvu.setText("Amdin");
    }




}