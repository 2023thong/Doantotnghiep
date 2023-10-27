package gmail.com.qlcafepoly.nhanvien;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gmail.com.qlcafepoly.R;


public class BanFragment extends Fragment {
    Button btnthemm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_ban_fragment, container, false);
        btnthemm = view.findViewById(R.id.btnthemm);

        btnthemm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuOrder.class);
                startActivity(intent);
            }
        });
        return view;
    }
}