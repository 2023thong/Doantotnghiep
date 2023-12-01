package gmail.com.qlcafepoly.nhanvien;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import gmail.com.qlcafepoly.R;

public class NhanvienMenu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien_menu);
        bottomNavigationView = findViewById(R.id.bonava1);



        Intent intent = getIntent();
        String maHH = intent.getStringExtra("chuyen");
        Log.d("DEBUG_TAG", "Dữ liệu nhận từ Intent: " + maHH);
        if (maHH == null) {
            replaceFragment(new ChonbanFragment());
        } else {
            replaceFragment(new QLthanhtoan_Fragment());
            bottomNavigationView.setSelectedItemId(R.id.tt);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.trChu) {
                    replaceFragment(new ChonbanFragment());
                }
                if (item.getItemId()==R.id.tt){
                    replaceFragment(new QLthanhtoan_Fragment());
                }
                if (item.getItemId()==R.id.nv){
                    replaceFragment(new NhanVienFragment());

                }
                return true;
        }

            });
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framg1, fragment);
        fragmentTransaction.commit();
    }

}
