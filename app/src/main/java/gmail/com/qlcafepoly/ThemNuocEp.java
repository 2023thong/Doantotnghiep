package gmail.com.qlcafepoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ThemNuocEp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nuoc_ep);
    }
    public void backthemnuocep(View view){
        Intent intent = new Intent(ThemNuocEp.this, Trangfruit.class);
        startActivity(intent);
    }
}