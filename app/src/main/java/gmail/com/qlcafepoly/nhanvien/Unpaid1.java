package gmail.com.qlcafepoly.nhanvien;



import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Hanghoaht;
import gmail.com.qlcafepoly.admin.Suathongtinhh;
import gmail.com.qlcafepoly.model.Ban;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Unpaid1 extends BaseAdapter {

    private final List<Thongtinoder> ttoder;
    private final LayoutInflater inflater;
    private final Context context;



    public Unpaid1 (Context unpaidFragment, List<Thongtinoder> ttoder) {
        this.ttoder = ttoder;
        this.context = unpaidFragment;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ttoder.size();
    }

    @Override
    public Object getItem(int position) {
        return ttoder.get(position);
    }

    @Override
    public long getItemId   (int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_unpaid, parent, false);
        }

        Thongtinoder thongtinoder = ttoder.get(position);
        TextView maOder = convertView.findViewById(R.id.tvMaOder);
        TextView maBn = convertView.findViewById(R.id.tvbantrong);
        TextView tongTien = convertView.findViewById(R.id.tvTongtien);
        Button button = convertView.findViewById(R.id.btnxemdanhsachban);
        Button btnThanhToan = convertView.findViewById(R.id.btnThanhToan);
        TextView ngay = convertView.findViewById(R.id.date);
        String currentDate = getCurrentDate();

        ngay.setText(currentDate);



        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 2) {

            // Gán giá trị cho các TextView
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));


            String tien = formatCurrency(thongtinoder.getTongTien());
            tongTien.setText(tien);

        }

            button.setOnClickListener(view -> {
            Intent intent = new Intent(context, Menu_pay.class);
            intent.putExtra("Maoder1",String.valueOf(thongtinoder.getMaOder()));
            intent.putExtra("Tongtien",String.valueOf(thongtinoder.getTongTien()));
            intent.putExtra("Mabn",String.valueOf(thongtinoder.getMaBn()));
            intent.putExtra("Ngay",currentDate);

            context.startActivity(intent);




        });


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý khi nhấn vào btnThanhToan
                Thanhtoan(String.valueOf(thongtinoder.getMaOder()), String.valueOf(1) );
                Trangthaibn(thongtinoder.getMaBn(), String.valueOf(1));



            }
        });


        Button btnSua = convertView.findViewById(R.id.btnSuaoder);
        // Assuming you have a Button or another View with click listener
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extract the data you want to send
                String maOder = String.valueOf(thongtinoder.getMaOder());
                String maBn = thongtinoder.getMaBn();
                String tongTien = String.valueOf(thongtinoder.getTongTien());

                // Create an Intent
                Intent intent = new Intent(context, SuaOder.class);

                // Put the data into the Intent
                intent.putExtra("MaOderoder", maOder);
                intent.putExtra("MaBnoder", maBn);
                intent.putExtra("TongTienoder", tongTien);

                // Start the new activity
                context.startActivity(intent);
                SharedPreferences sharedPreferences = context.getSharedPreferences("menu1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Maoder", maOder);  // Replace "TenDn" with your key and TenDn with the value you want to store
                editor.apply();
            }
        });




        return convertView;
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);

    }


    public void Thanhtoan(String MaOder , String Trangthai) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Thongtinoder user = new Thongtinoder();
        user.setMaOder(Integer.parseInt(MaOder));
        user.setTrangThai(Integer.parseInt(Trangthai));

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THANHTOAN);
        serverRequest.setThongtinoder(user);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);


        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                assert response1 != null;
                if (response1.getResult().equals(Constants.SUCCESS)){

                    Toast.makeText(context, response1.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(context, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed"+ t.getMessage());

            }
        });

    }

    private void button(String MaOder) {
        Intent intent = new Intent(context, Menu_pay.class);
        intent.putExtra("Maoder1", MaOder);
        context.startActivity(intent);
    }

    public void Trangthaibn(String MaBn, String Trangthai) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Ban ban = new Ban();
        ban.setMaBn(MaBn);


        ban.setTrangthai(Trangthai);


        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUABAN);
        serverRequest.setBan(ban);


        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
//                    Toast.makeText(context, response1.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }

        });
    }
    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(value);
    }





}