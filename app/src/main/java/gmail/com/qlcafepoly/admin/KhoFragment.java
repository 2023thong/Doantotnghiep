package gmail.com.qlcafepoly.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KhoFragment extends Fragment {
    private EditText edMahh, edMancc, edMalh, edTenhh, edGiatien, edGhichu;
    private TextView textView5;
    private Button buttonSave;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho, container, false);

        edMahh = view.findViewById(R.id.tvMahh);
        edMancc = view.findViewById(R.id.edMncc);
        edMalh = view.findViewById(R.id.Malh);
        edTenhh = view.findViewById(R.id.edTenhh);
        edGiatien = view.findViewById(R.id.edGiatien);
        edGhichu = view.findViewById(R.id.edGhichu);

        textView5 = view.findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThongTinHangNhap.class);
                startActivity(intent);
            }
        });


        buttonSave = view.findViewById(R.id.btnLuukho);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                    String mahh = edMahh.getText().toString();
                    String mancc = edMancc.getText().toString();
                    String malh = edMalh.getText().toString();
                    String tehh = edTenhh.getText().toString();
                    String giatien = edGiatien.getText().toString();
                    String ghichu = edGhichu.getText().toString();
                    registerProcess1(mahh, mancc, malh, tehh, giatien, ghichu);

                edMahh.setText("");
                edMancc.setText("");
                edTenhh.setText("");
                edMalh.setText("");
                edGiatien.setText("");
                edGhichu.setText("");


            }
        });

        return view;
    }


    public void registerProcess1(String MaHH , String MaNcc, String MaLh,String TenHh,String GiaSp,String Ghichu ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User1 user1 = new User1();
        user1.setMaHH(MaHH);
        user1.setMaNcc(MaNcc);
        user1.setMaLh(MaLh);
        user1.setTenHh(TenHh);
        user1.setGiaSp(GiaSp);
        user1.setGhichu(Ghichu);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.HANGHOA);
        serverRequest.setUser1(user1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);


        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối đến CSDL khi Fragment bị hủy

    }
}
