package gmail.com.qlcafepoly.nhanvien;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import gmail.com.qlcafepoly.R;

public class NenFragment extends Fragment {
    Button btnbanodr, btndanhsach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nen, container, false);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        btnbanodr = (Button) view.findViewById(R.id.btnbanodr);
        btndanhsach = (Button) view.findViewById(R.id.btndanhsach);
        BanFragment banFragment = new BanFragment();
        manager.beginTransaction().replace(R.id.frag2,banFragment).commit();
        btnbanodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BanFragment banFragment = new BanFragment();
                manager.beginTransaction().replace(R.id.frag2,banFragment).commit();
            }
        });
        btndanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhsachFragment danhsachFragment = new DanhsachFragment();
                manager.beginTransaction().replace(R.id.frag2,danhsachFragment).commit();
            }
        });
        return view;
    }
}