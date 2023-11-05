package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Suathongtinhh extends AppCompatActivity {
    private EditText edMahh,  edTenhh, edGiatien, edGhichu, edSoluong;
    private TextView textView5 ,edMancc, edMalh;
    private Button buttonSave;
    private ImageView imageView;
    ThongTinHangNhap thongTinHangNhap;

    Button btnquaylai, btnSua;
    TextView Mahh;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtinhh);

        Intent intent = getIntent();
        String maHH = intent.getStringExtra("DULIEU");
        String maNcc = intent.getStringExtra("DULIEU_MaNcc");
        String maLh = intent.getStringExtra("DULIEU_MaLh");
        String Tehh = intent.getStringExtra("DULIEU_TenHh");
        String Gia = intent.getStringExtra("DULIEU_GiaSp");
        String ghichu = intent.getStringExtra("DULIEU_Ghichu");
        String soluong = intent.getStringExtra("DULIEU_Soluong");

        TextView textViewMaHH = findViewById(R.id.tvManccs1);
        TextView textViewMaNcc = findViewById(R.id.edManccs);
        TextView textViewMlh = findViewById(R.id.edMalhs);
        TextView textViewTehh = findViewById(R.id.edTenhhs);
        TextView textViewgia = findViewById(R.id.edTennccs);
        TextView textViewGhichu = findViewById(R.id.edDiachis);
        TextView textViewsoluong = findViewById(R.id.edSDts);

        textViewMaHH.setText(maHH);
        textViewMaNcc.setText(maNcc);
        textViewMlh.setText(maLh);
        textViewTehh.setText(Tehh);
        textViewgia.setText(Gia);
        textViewGhichu.setText(ghichu);
        textViewsoluong.setText(soluong);
        btnSua = findViewById(R.id.btnSuas);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mahh = textViewMaHH.getText().toString();
                String mancc = textViewMaNcc.getText().toString();
                String malh = textViewMlh.getText().toString();
                String tehh = textViewTehh.getText().toString();
                String giatien = textViewgia.getText().toString();
                String ghichu = textViewGhichu.getText().toString();
                String soluong = textViewsoluong.getText().toString();

                Suahanghoa(mahh, mancc, malh, tehh, giatien, ghichu, soluong);
                textViewMaHH.setText("");
                textViewMaNcc.setText("");
                textViewMlh.setText("");
                textViewTehh.setText("");
                textViewgia.setText("");
                textViewGhichu.setText("");
                textViewsoluong.setText("");






            }
        });

        btnquaylai = findViewById(R.id.btnQuaylais);
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void Suahanghoa(String MaHH , String MaNcc, String TenLh,String TenHh,String GiaSp,String Ghichu, String Soluong ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User1 user1 = new User1();
        user1.setMaHH(MaHH);
        user1.setMaNcc(MaNcc);
        user1.setTenLh(TenLh);
        user1.setTenHh(TenHh);
        user1.setGiaSp(GiaSp);
        user1.setGhichu(Ghichu);
        user1.setSoluong(Soluong);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUAHANGHOA);
        serverRequest.setUser1(user1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" +t.getMessage());

            }
        });
    }



}