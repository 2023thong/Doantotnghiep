package gmail.com.qlcafepoly.nhanvien;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import gmail.com.qlcafepoly.R;

public class Pay_UnpaidFragment extends Fragment {

    Button bt_chuathanhtoan, bt_thanhtoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_unpaid, container, false);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        bt_chuathanhtoan = (Button) view.findViewById(R.id.bt_chuathanhtoan);
        bt_thanhtoan = (Button) view.findViewById(R.id.bt_thanhtoan);
        UnpaidFragment unpaidFragment = new UnpaidFragment();
        manager.beginTransaction().replace(R.id.frag4,unpaidFragment).commit();
        // Không cần khởi tạo Pay_UnpaidFragment ở đây

        bt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayFragment payFragment = new PayFragment();
                manager.beginTransaction().replace(R.id.frag4, payFragment).commit();

//                UnpaidFragment unpaidFragment = new UnpaidFragment();
//                manager.beginTransaction().replace(R.id.frag4, unpaidFragment).commit();
            }
        });
        bt_chuathanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnpaidFragment unpaidFragment = new UnpaidFragment();
                manager.beginTransaction().replace(R.id.frag4, unpaidFragment).commit();
//                PayFragment payFragment = new PayFragment();
//                manager.beginTransaction().replace(R.id.frag4, payFragment).commit();
            }
        });
        return view;
    }
}
