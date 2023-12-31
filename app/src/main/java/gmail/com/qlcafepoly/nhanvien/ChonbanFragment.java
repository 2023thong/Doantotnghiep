package gmail.com.qlcafepoly.nhanvien;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.BanFragment;
import gmail.com.qlcafepoly.nhanvien.TableActivity;


public class ChonbanFragment extends Fragment {
    FloatingActionButton quaTable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChonbanFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BanFragment newInstance(String param1, String param2) {
        BanFragment fragment = new BanFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chonban, container, false);
//        FragmentManager manager = getChildFragmentManager();
        quaTable =(FloatingActionButton) view.findViewById(R.id.quatable);
        quaTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TableActivity.class);

                startActivity(intent);
            }
        });
        return view;
    }
}