package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;

public class Trangfruit extends AppCompatActivity {
    private List<Menu> lsuListMenu = new ArrayList<>();
    private Menuht adepter;
    private ListView lshienthimenu;
    private String urllink = "http://192.168.1.7:8080/duantotnghiep/get_all_menu.php";

    private ProgressDialog pd;
    private ImageView btnThemnuocep, btnthoatcofe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangfruit);
    }
//    public void backcofee(View view){
//        Intent intent = new Intent(Trangfruit.this,QuanLyDoUong.class);
//        startActivity(intent);
//    }
    public void themnuocep(View view) {
        Intent intent = new Intent(Trangfruit.this, ThemNuocEp.class);
        startActivity(intent);
    }
    public void backcofee(View view){
        Intent intent = new Intent(Trangfruit.this,QuanLyDoUong.class);
        startActivity(intent);
    }
    public void backfruit(View view){
        Intent intent = new Intent(Trangfruit.this, QuanLyDoUong.class);
        startActivity(intent);
    }
}