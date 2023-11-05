package gmail.com.qlcafepoly.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.dangnhap.Danhnhap;
import gmail.com.qlcafepoly.dangnhap.DoiMatKhau;


public class CanhanFragment extends Fragment {
    ConstraintLayout tvttcn;
    ConstraintLayout layout, doimk;
    TextView textView;





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);
        tvttcn = view.findViewById(R.id.tvTtcn);
        layout = view.findViewById(R.id.dangxuat);
        tvttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Thonhtintaikhoan.class);
                startActivity(intent);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });
        doimk = view.findViewById(R.id.doimatkhau);
        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiMatKhau.class));
            }
        });
        textView = view.findViewById(R.id.tvTen);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("thong", Context.MODE_PRIVATE);

        String Tennv = sharedPreferences.getString("TenNv", "");

        textView.setText(Tennv);


        return view;
    }


    public void logout(View view) {
        // Xóa trạng thái đăng nhập
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Điều hướng đến màn hình đăng nhập hoặc màn hình khác tùy ý
        Intent intent = new Intent(getActivity(), Danhnhap.class);
        startActivity(intent);
        getActivity().finish();




    }
}