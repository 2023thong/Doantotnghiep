<<<<<<< HEAD:app/src/main/java/gmail/com/qlcafepoly/admin/NhanvienMenu.java
package gmail.com.qlcafepoly.admin;
=======

package gmail.com.qlcafepoly.nhanvien;
>>>>>>> cf972eb8fde6d07965cdea23e836e51599f5399a:app/src/main/java/gmail/com/qlcafepoly/nhanvien/NhanvienMenu.java

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

<<<<<<< HEAD:app/src/main/java/gmail/com/qlcafepoly/admin/NhanvienMenu.java
<<<<<<< HEAD
=======
import gmail.com.qlcafepoly.ChonbanFragment;
>>>>>>> 61ce38818051f51f2ee8b80b82a3cef4b0bb4a1e
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.QuanlyFragment;
=======
import gmail.com.qlcafepoly.R;
>>>>>>> cf972eb8fde6d07965cdea23e836e51599f5399a:app/src/main/java/gmail/com/qlcafepoly/nhanvien/NhanvienMenu.java

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
                if (item.getItemId()==R.id.trChu){
                    replaceFragment(new ChonbanFragment());
                }
                if (item.getItemId()==R.id.nv){
//                    replaceFragment(new QuanlyFragment());
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