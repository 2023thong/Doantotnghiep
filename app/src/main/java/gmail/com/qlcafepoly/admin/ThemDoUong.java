package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ThemDoUong extends AppCompatActivity {
    private EditText edMaMn, edTenLh, edGiatien;
    private Button btnThemmenu, btnxemmenu;
    private View backThemDU;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_uong);
        edMaMn = findViewById(R.id.edtMaMn);
        edTenLh =findViewById(R.id.edtTenLh);
        edGiatien =findViewById(R.id.edtGiaTien);
        btnThemmenu =findViewById(R.id.btnThemmenu);
        btnxemmenu = findViewById(R.id.btnxemmenu);
        backThemDU = findViewById(R.id.backThemDU);
        btnThemmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mamn = edMaMn.getText().toString();
                String tendu = edTenLh.getText().toString();
                String giatien = edGiatien.getText().toString();

                if (mamn.isEmpty() || tendu.isEmpty() || giatien.isEmpty()) {
                    Toast.makeText(ThemDoUong.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate Giatien
                    if (!isNumeric(giatien)) {
                        Toast.makeText(ThemDoUong.this, "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
                        return; // Do not proceed if Giatien is not a number.
                    }

                    registerMenu(mamn, tendu, giatien);

                    edMaMn.setText("");
                    edTenLh.setText("");
                    edGiatien.setText("");
                }
            }
        });
        btnxemmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        backThemDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Allows integers and decimals
    }
    public void registerMenu(String MaMn , String TenDu, String Giatien ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Menu menu = new Menu();
        menu.setMaMn(MaMn);
        menu.setTenDu(TenDu);
        menu.setGiatien(Integer.parseInt(String.valueOf(Giatien)));
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.MENU);
        serverRequest.setMenu(menu);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(ThemDoUong.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ThemDoUong.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed"+ t.getMessage());

            }
        });
    }
}