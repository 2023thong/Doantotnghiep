package gmail.com.qlcafepoly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminKho extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_kho);
        bottomNavigationView = findViewById(R.id.bonava);
        replaceFragment(new CanhanFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.kho){
                    replaceFragment(new KhoFragment());
                }

                if (item.getItemId()==R.id.ql){
                    replaceFragment(new QuanlyFragment());
                }

                if (item.getItemId()==R.id.canhan){
                    replaceFragment(new CanhanFragment());
                }


                return true;
            }
        });



    }




    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framg, fragment);
        fragmentTransaction.commit();
    }
}