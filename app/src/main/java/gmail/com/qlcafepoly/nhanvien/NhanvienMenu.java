package gmail.com.qlcafepoly.nhanvien;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import gmail.com.qlcafepoly.R;


import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Unpaid;


public class NhanvienMenu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien_menu);
        bottomNavigationView = findViewById(R.id.bonava1);
        replaceFragment(new ChonbanFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.trChu) {
                    replaceFragment(new ChonbanFragment());
                }

                if (item.getItemId()==R.id.tt){
                    Intent intent = new Intent(NhanvienMenu.this, Unpaid.class);
                    startActivity(intent);


                }

                if (item.getItemId()==R.id.nv){
//                    replaceFragment(new Unpaid());

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
