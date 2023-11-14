package gmail.com.qlcafepoly.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Menu;

public class SuaOder extends AppCompatActivity {
    private OderHienthi adepteroder;
    private ListView lshienthimenu;
    private List<Menu> odermenu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_oder);

        lshienthimenu = findViewById(R.id.lshienmnsua);
        adepteroder = new OderHienthi(SuaOder.this, odermenu);
        lshienthimenu.setAdapter(adepteroder);
    }
}