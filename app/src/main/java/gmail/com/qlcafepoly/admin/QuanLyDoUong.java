package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import gmail.com.qlcafepoly.R;

public class QuanLyDoUong extends AppCompatActivity {
    TextView tvDoUongKhac,tvCF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_do_uong);
        tvDoUongKhac = findViewById(R.id.tvDoUongKhac);
        tvCF = findViewById(R.id.tvCF);

            tvCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoUong.this, Trangcoffee.class);
                Toast.makeText(QuanLyDoUong.this,"Vào trang Coffee thành công!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        tvDoUongKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoUong.this, Trangdouongkhac.class);
                Toast.makeText(QuanLyDoUong.this,"Vào trang thành công!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
//    public void tvCF(View view){
//        Intent intent = new Intent(QuanLyDoUong.this, Trangcoffee.class);
//        startActivity(intent);
//    }
    public void tvFruit(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangfruit.class);
        Toast.makeText(QuanLyDoUong.this,"Vào trang nước ép thành công!",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    public void tvTrs(View view){
        Intent intent = new Intent(QuanLyDoUong.this, Trangmilktea.class);
        Toast.makeText(QuanLyDoUong.this,"Vào trang Trà sữa thành công!",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
//    public void tvDoUongKhac(View view){
//        Intent intent = new Intent(QuanLyDoUong.this, Trangdouongkhac.class);
//        startActivity(intent);
//    }
    public void backDouong(View view){
        finish();
    }
    public void backhome(View view){
        finish();
    }
}