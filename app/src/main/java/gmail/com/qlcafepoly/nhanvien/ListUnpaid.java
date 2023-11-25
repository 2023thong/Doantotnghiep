package gmail.com.qlcafepoly.nhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import gmail.com.qlcafepoly.R;

public class ListUnpaid extends AppCompatActivity {
    Button btnXemdanhsachban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unpaid);
        btnXemdanhsachban = findViewById(R.id.btnxemdanhsachban);
        btnXemdanhsachban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListUnpaid.this, Menu_pay.class);
                startActivity(intent);
            }
        });

    }
}
