package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class ThemDoUongKhac extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_uong_khac);
    }
    public void backthemdouongkhac(View view){
        Intent intent = new Intent(ThemDoUongKhac.this, Trangdouongkhac.class);
        startActivity(intent);
    }
}