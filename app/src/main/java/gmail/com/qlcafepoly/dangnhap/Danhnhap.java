package gmail.com.qlcafepoly.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import gmail.com.qlcafepoly.AdminKho;
import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.KhoFragment;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.User;
import gmail.com.qlcafepoly.User1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Danhnhap extends AppCompatActivity {
    TextView tvQuanly, tvNhanvien;
    Button btnDn;
    EditText edTedn, edPass;
    CheckBox rememberCheckbox;
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhnhap);

        rememberCheckbox = findViewById(R.id.checkBox2);

        // Kiểm tra nếu checkbox đã được tích thì điền thông tin đăng nhập từ SharedPreferences
        if (rememberCheckbox.isChecked()) {
            // Lấy SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String savedTenDn = sharedPreferences.getString("TenDn", "");
            String savedMatkhau = sharedPreferences.getString("Matkhau", "");

            // Điền thông tin vào các trường EditText
            edTedn.setText(savedTenDn);
            edPass.setText(savedMatkhau);
        }

        tvQuanly = findViewById(R.id.tvQuanly);

        tvNhanvien = findViewById(R.id.tvNhanvien);
        edTedn = findViewById(R.id.edTendn);
        edPass = findViewById(R.id.edPass);
        tvNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Danhnhap.this, Dangnhapnhanvien.class);
                startActivity(intent);
            }
        });
        btnDn = findViewById(R.id.btnDangnhap);
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendn = edTedn.getText().toString();
                String pas = edPass.getText().toString();
                Dangnhap(tendn, pas);
            }
        });


    }
    public void Dangnhap(String TenDn , String Matkhau) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setTenDn(TenDn);
        user.setMatkhau(Matkhau);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.LOGIN);
        serverRequest.setUser(user);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);


        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    String role = response1.getPhanquyen(); // Lấy vai trò từ kết quả API
                    if ("1".equals(role)) {
                        Toast.makeText(Danhnhap.this, "Đăng nhập thành công Quản lý", Toast.LENGTH_SHORT).show();
                        Intent adminIntent = new Intent(getApplicationContext(), AdminKho.class);
                        startActivity(adminIntent);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TenDn", TenDn);
                        editor.putString("Matkhau", Matkhau);
                        editor.putBoolean("RememberLogin", true);
                        editor.apply();
                    } else if ("2".equals(role)) {
                        Toast.makeText(Danhnhap.this, "Đăng nhập không thành công.\n" +
                                "Chỉ dành cho admin",Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }




}