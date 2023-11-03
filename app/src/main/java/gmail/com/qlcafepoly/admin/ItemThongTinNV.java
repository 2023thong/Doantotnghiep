package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.List;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemThongTinNV extends AppCompatActivity {
    Button btnBackNV,btnSuaNV;
    EditText edMaNV,edTenNV,edTenDN,edMatKhau,edSdt,edDiaChi,edChucVu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_thong_tin_nv);
        edMaNV = findViewById(R.id.edMaNV);
        edTenNV =findViewById(R.id.edTenNV);
        edTenDN =findViewById(R.id.edTenDN);
        edMatKhau =findViewById(R.id.edMatKhau);
        edSdt =findViewById(R.id.edSdt);
        edDiaChi =findViewById(R.id.edDiaChi);
        edChucVu =findViewById(R.id.edChucVu);
        btnBackNV = findViewById(R.id.btnBackNV);
        btnSuaNV = findViewById(R.id.btnSuaNV);

        btnSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = edMaNV.getText().toString();
                String tennv = edTenNV.getText().toString();
                String tendn = edTenDN.getText().toString();
                String matkhau = edMatKhau.getText().toString();
                String sdt = edSdt.getText().toString();
                String diachi = edDiaChi.getText().toString();
                String chucvu = edChucVu.getText().toString();
                registerProcess2(manv, tennv, tendn, matkhau, sdt, diachi, chucvu);
            }
        });
        btnBackNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemThongTinNV.this, Quanlynv.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String MaNv = intent.getStringExtra("DULIEUNV");
        String TenNv = intent.getStringExtra("DULIEUNV_TenNv");
        String TenDn = intent.getStringExtra("DULIEUNV_TenDn");
        String Matkhau = intent.getStringExtra("DULIEUNV_Matkhau");
        String Sdt = intent.getStringExtra("DULIEUNV_Sdt");
        String Diachi = intent.getStringExtra("DULIEUNV_Diachi");
        String Chucvu = intent.getStringExtra("DULIEUNV_Chucvu");

        EditText edMaNv = findViewById(R.id.edMaNV);
        EditText edTenNv = findViewById(R.id.edTenNV);
        EditText edTenDn = findViewById(R.id.edTenDN);
        EditText edMatkhau = findViewById(R.id.edMatKhau);
        EditText edSdt = findViewById(R.id.edSdt);
        EditText edDiachi = findViewById(R.id.edDiaChi);
        EditText edChucvu = findViewById(R.id.edChucVu);

        edMaNv.setText(MaNv);
        edTenNv.setText(TenNv);
        edTenDn.setText(TenDn);
        edMatkhau.setText(Matkhau);
        edSdt.setText(Sdt);
        edDiachi.setText(Diachi);
        edChucvu.setText(Chucvu);
    }
    public void registerProcess2(String MaNv , String TenNv, String TenDn,String Matkhau,String Sdt,String Diachi, String Chucvu ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setMaNv(MaNv);
        user.setTenNv(TenNv);
        user.setTenDn(TenDn);
        user.setMatkhau(Matkhau);
        user.setSdt(Sdt);
        user.setDiachi(Diachi);
        user.setChucvu(Integer.parseInt(Chucvu));
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUANHANVIEN);
        serverRequest.setUser(user);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }
}