package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Trangfruit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangfruit);
    }
//    public void backcofee(View view){
//        Intent intent = new Intent(Trangfruit.this,QuanLyDoUong.class);
//        startActivity(intent);
//    }
    public void themnuocep(View view){
        Intent intent = new Intent(Trangfruit.this,ThemNuocEp.class);
        startActivity(intent);
    }
    public void backfruit(View view){
        Intent intent = new Intent(Trangfruit.this, QuanLyDoUong.class);
        startActivity(intent);
    }
}