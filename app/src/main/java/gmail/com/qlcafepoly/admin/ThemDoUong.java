package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class ThemDoUong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_uong);
    }
    public void backthemdouong(View view){
        Intent intent = new Intent(ThemDoUong.this, Trangcoffee.class);
        startActivity(intent);
    }
}