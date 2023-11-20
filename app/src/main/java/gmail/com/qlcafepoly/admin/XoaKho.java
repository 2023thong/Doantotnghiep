package gmail.com.qlcafepoly.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class XoaKho extends AppCompatActivity {
    private ImageView imagxoa;
    private TextView mahh;
//    tôi chạy thử
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hienthikhohang);


        mahh =  findViewById(R.id.tvMahh1);
        imagxoa = (ImageView) findViewById(R.id.btnXoahanghoa);


    }

//    private void deleteItem(String MaHH) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
//        User1 user1 = new User1();
//        user1.setMaHH(MaHH);  // Set the item ID to be deleted
//        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
//        serverRequest.setOperation(Constants.XOAHH);
//        serverRequest.setUser1(user1);
//
//        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
//        responseCall.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse response1 = response.body();
//                if (response1.getResult().equals(Constants.SUCCESS)) {
//                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
//                    Ex
//                } else {
//                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
//                    // Handle failure
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                // Handle network failure
//                t.printStackTrace();
//            }
//        });
//    }

}
