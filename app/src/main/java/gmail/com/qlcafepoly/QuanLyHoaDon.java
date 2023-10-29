package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class QuanLyHoaDon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hoa_don);
    }
    public void backHoaDon (View view){
        finish();
    }
}