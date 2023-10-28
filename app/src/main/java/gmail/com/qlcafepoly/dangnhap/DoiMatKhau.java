package gmail.com.qlcafepoly.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoiMatKhau extends AppCompatActivity {
    private EditText edmkcu, edmkmoi, ednlmkmoi;

    private Button buttonSave;
    private ImageView buttonback;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        edmkcu = findViewById(R.id.mkcu);
        edmkmoi = findViewById(R.id.mkmoi);
        ednlmkmoi = findViewById(R.id.nlmkmoi);

        buttonback = findViewById(R.id.backdoimk);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        buttonSave = findViewById(R.id.btndoimk);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mkcu = edmkcu.getText().toString();
                String mkmoi = edmkmoi.getText().toString();
                String mkmoi1 = ednlmkmoi.getText().toString();

                

                if (!mkmoi1.equals(mkmoi)) {
                    Toast.makeText(DoiMatKhau.this, "Mật khẩu mới không giống nhau", Toast.LENGTH_SHORT).show();
                }

                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("thong", Context.MODE_PRIVATE);
                    String Tendn = sharedPreferences.getString("TenDn", "");
                    Doimatkhau(Tendn, mkcu, mkmoi);

                    edmkmoi.setText("");
                    edmkcu.setText("");
                    ednlmkmoi.setText("");

                }





            }
        });



    }
    public void Doimatkhau(String Tendn, String passwordcu, String passwordmoi ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("thong", Context.MODE_PRIVATE);
        Tendn = sharedPreferences.getString("TenDn", "");

        User user1 = new User();
        user1.setTenDn(Tendn);
        user1.setPasswordcu(passwordcu);
        user1.setPasswordmoi(passwordmoi);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.DOIMATKHAUADMIN);
        serverRequest.setUser(user1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);


        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
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




    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}