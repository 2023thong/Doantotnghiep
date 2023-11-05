package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gmail.com.qlcafepoly.R;

public class ThemTraSua extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tra_sua);
    }
    public void backthemtrasua(View view){
        Intent intent = new Intent(ThemTraSua.this, Trangmilktea.class);
        startActivity(intent);
    }
}