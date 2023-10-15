package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class Trangcoffee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcoffee);
    }
    public void themdouong(View view){
        Intent intent = new Intent(Trangcoffee.this,ThemDoUong.class);
        startActivity(intent);
    }
    public void backcofee(View view){
        Intent intent = new Intent(Trangcoffee.this, QuanLyDoUong.class);
        startActivity(intent);
    }
}