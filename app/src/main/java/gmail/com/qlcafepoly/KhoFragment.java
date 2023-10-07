package gmail.com.qlcafepoly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import gmail.com.qlcafepoly.Database.Csdl;

public class KhoFragment extends Fragment {
    private EditText edMahh, edMancc, edMalh, edTenhh, edGiatien, edGhichu;
    private Button buttonSave;
    private Csdl databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new Csdl(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho, container, false);

        edMahh = view.findViewById(R.id.edMahh);
        edMancc = view.findViewById(R.id.edMncc);
        edMalh = view.findViewById(R.id.Malh);
        edTenhh = view.findViewById(R.id.edTenhh);
        edGiatien = view.findViewById(R.id.edGiatien);
        edGhichu = view.findViewById(R.id.edGhichu);
        buttonSave = view.findViewById(R.id.btnLuukho);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahh = edMahh.getText().toString();
                String mancc = edMancc.getText().toString();
                String malh = edMalh.getText().toString();
                String tenhh = edTenhh.getText().toString();
                String ghichu = edGhichu.getText().toString();
                int giatien = Integer.parseInt(edGiatien.getText().toString());

                // Lưu dữ liệu vào CSDL
                long newRowId = databaseHelper.addData(mahh, mancc, malh, tenhh, ghichu, giatien);

                if (newRowId != -1) {
                    // Thêm dữ liệu thành công
                    Toast.makeText(getContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xảy ra lỗi khi thêm
                    Toast.makeText(getContext(), "Lỗi khi thêm dữ liệu", Toast.LENGTH_SHORT).show();
                }

                // Xóa nội dung trong EditText sau khi lưu
                edMahh.setText("");
                edMancc.setText("");
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối đến CSDL khi Fragment bị hủy
        databaseHelper.close();
    }
}
