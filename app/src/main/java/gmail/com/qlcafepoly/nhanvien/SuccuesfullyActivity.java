package gmail.com.qlcafepoly.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.NhanvienMenu;

public class SuccuesfullyActivity extends AppCompatActivity {
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succuesfully);
        btn_back = findViewById(R.id.btn_backTrangChu);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccuesfullyActivity.this, NhanvienMenu.class);
                startActivity(intent);
                finish();
            }
        });
    }

}