package gmail.com.qlcafepoly.thanhtoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gmail.com.qlcafepoly.R;

public class Pay extends AppCompatActivity {
    TextView tvchuathanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        tvchuathanhtoan = findViewById(R.id.tvchuathanhtoan);
        tvchuathanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pay.this,Unpaid.class);
                startActivity(intent);
            }
        });
    }



}