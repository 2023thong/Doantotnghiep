package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Trangmilktea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangmilktea);
    }
    public void backtrangmilktea(View view){
        Intent intent = new Intent(Trangmilktea.this,QuanLyDoUong.class);
        startActivity(intent);
    }
    public void themtrasua(View view){
        Intent intent = new Intent(Trangmilktea.this,ThemTraSua.class);
        startActivity(intent);
    }
}