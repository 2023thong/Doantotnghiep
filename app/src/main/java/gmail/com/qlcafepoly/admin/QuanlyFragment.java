package gmail.com.qlcafepoly.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gmail.com.qlcafepoly.R;

public class QuanlyFragment extends Fragment {
    TextView tvQLNV, tvQLDU,tvQLHD;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuanlyFragment() {
        // Required empty public constructor
    }
    public static QuanlyFragment newInstance(String param1, String param2) {
        QuanlyFragment fragment = new QuanlyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quanly, container, false);
        tvQLNV = view.findViewById(R.id.tvQLNV);
        tvQLDU = view.findViewById(R.id.tvQLDU);
        tvQLHD = view.findViewById(R.id.tvQLHD);
        tvQLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Quanlynv.class);
                startActivity(intent);
            }
        });
        tvQLDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuanLyDoUong.class);
                startActivity(intent);
            }
        });
        tvQLHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuanLyHoaDon.class);
                startActivity(intent);
            }
        });
        return view;
    }
}