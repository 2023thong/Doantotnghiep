package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.ThemTraSua;

public class Trangmilktea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangmilktea);
    }

    public void backcofee(View view) {
        Intent intent = new Intent(Trangmilktea.this, QuanLyDoUong.class);
    }

    public void backtrangmilktea(View view) {
        Intent intent = new Intent(Trangmilktea.this, QuanLyDoUong.class);

        startActivity(intent);
    }
    public void themtrasua(View view){
        Intent intent = new Intent(Trangmilktea.this, ThemTraSua.class);
        startActivity(intent);
    }
}