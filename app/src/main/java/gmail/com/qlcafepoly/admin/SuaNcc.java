package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SuaNcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ncc);

        Intent intent = getIntent();
        String mancc = intent.getStringExtra("DULIEU");
        String tenncc = intent.getStringExtra("DULIEU_Tenncc");
        String diachi = intent.getStringExtra("DULIEU_Diachi");
        String sdt = intent.getStringExtra("DULIEU_sdt");


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView MaNcc = findViewById(R.id.tvManccs1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})TextView Tencc = findViewById(R.id.edTennccs);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})TextView Diachi = findViewById(R.id.edDiachis);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})TextView SDT = findViewById(R.id.edSDts);

        MaNcc.setText(mancc);
        Tencc.setText(tenncc);
        Diachi.setText(diachi);
        SDT.setText(sdt);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnSua = findViewById(R.id.btnSuas);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mancc = MaNcc.getText().toString();
                String tenncc = Tencc.getText().toString();
                String diachi = Diachi.getText().toString();
                String sdt = SDT.getText().toString();


                Suanhacc(mancc, tenncc, diachi, sdt);
                MaNcc.setText("");
                Tencc.setText("");
                Diachi.setText("");
                SDT.setText("");







            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnquaylai = findViewById(R.id.btnQuaylais);
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void Suanhacc(String MaNcc , String TenNcc, String Diachi,String Sdt) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User2 user2 = new User2();
        user2.setMaNcc(MaNcc);
        user2.setTenNcc(TenNcc);
        user2.setDiachi(Diachi);
        user2.setSdt(Sdt);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUANHACUNGCAP);
        serverRequest.setUser2(user2);
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
