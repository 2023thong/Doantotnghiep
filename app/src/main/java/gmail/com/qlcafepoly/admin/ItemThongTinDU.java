package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class ItemThongTinDU extends AppCompatActivity {
Button btnBack,btnSuaDU,btnXoaDU;
    EditText edMaMN,edTenLH,edGiaTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_thong_tin_du);
        edMaMN = findViewById(R.id.edMaDU);
        edTenLH =findViewById(R.id.edTenDU);
        edGiaTien =findViewById(R.id.edGiaDU);
        btnSuaDU = findViewById(R.id.btnSuaDU);
        btnBack = findViewById(R.id.btnBack);
        btnXoaDU = findViewById(R.id.btnXoaDU);
        btnSuaDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mamn = edMaMN.getText().toString();
                String tenlh = edTenLH.getText().toString();
                String giatien = edGiaTien.getText().toString();
                EditMenu(mamn, tenlh, giatien);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String MaMn = intent.getStringExtra("DULIEUDU");
        String TenLh = intent.getStringExtra("DULIEUDU_TenLh");
        String Giatien = intent.getStringExtra("DULIEUDU_Giatien");

        EditText edMaMn = findViewById(R.id.edMaDU);
        EditText edTenLh = findViewById(R.id.edTenDU);
        EditText edGiatien = findViewById(R.id.edGiaDU);

        edMaMn.setText(MaMn);
        edTenLh.setText(TenLh);
        edGiatien.setText(Giatien);
        btnXoaDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Người dùng đã xác nhận xóa, thực hiện hành động xóa ở đây
                String mamn = edMaMN.getText().toString();
                deleteMenu(mamn);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Người dùng đã chọn không, đóng dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void EditMenu(String MaMn , String TenLh, String Giatien) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Menu menu = new Menu();
        menu.setMaMn(MaMn);
        menu.setTenLh(TenLh);
        menu.setGiatien(Integer.parseInt(Giatien));
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUAMENU);
        serverRequest.setMenu(menu);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }
    public void deleteMenu( final String MaMn) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Menu menu = new Menu();
        menu.setMaMn(MaMn);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.XOAMENU);
        serverRequest.setMenu(menu);

        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    // Sau khi xóa thành công, quay về màn hình Quanlynv hoặc thực hiện các xử lý khác tùy ý.
                    Intent intent = new Intent(ItemThongTinDU.this, DanhSachDoUong.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");
            }
        });
    }
}