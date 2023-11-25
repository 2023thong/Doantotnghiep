package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.model.Ban;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Unpaid extends AppCompatActivity {


    private List<Thongtinoder> listUnpaid = new ArrayList<>();
    private Button btnThanhToan;
    private TextView tvTrangthai, tvTime, tvDate;
    private TextView textViewMarquee;
    private Unpaid1 unpaid1;
    private ImageView imageView;
    private ListView lv_unpaid;

    private String urllink = BASE_URL + "duantotnghiep/trangthaithanhtoan.php";
    private ProgressDialog pd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        tvTrangthai = findViewById(R.id.tvTrangthai);
        imageView = findViewById(R.id.img_Douong);
        lv_unpaid = findViewById(R.id.lv_unpaid);


        unpaid1 = new Unpaid1(Unpaid.this, listUnpaid);
        lv_unpaid.setAdapter(unpaid1);
        pd = new ProgressDialog(Unpaid.this);

        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);


        new MyAsyncTask().execute(urllink);

        TextView tvdathanhtoan = findViewById(R.id.tvdathanhtoan);
        tvdathanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pay.class);
                startActivity(intent);
            }
        });

    }






    class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Đang tải dữ liệu...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String strJson = readJsonOnline(strings[0]);
                Log.d("//====", strJson);

                JSONObject jsonObject = new JSONObject(strJson);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray jsonArrayPay = jsonObject.getJSONArray("oder");
                    Log.d("//=====size=====", jsonArrayPay.length() + "");
                    for (int i = 0; i < jsonArrayPay.length(); i++) {
                        JSONObject PayObject = jsonArrayPay.getJSONObject(i);
                        Log.d("MaOder", PayObject.getString("MaOder"));
                        Log.d("MaBn", PayObject.getString("MaBn"));
                        Log.d("TongTien", PayObject.getString("TongTien"));
                        Log.d("TrangThai", PayObject.getString("TrangThai"));
                        String MaOder = PayObject.getString("MaOder");
                        String MaBn = PayObject.getString("MaBn");

                        int TrangThai = PayObject.getInt("TrangThai");
                        String Tongtien = PayObject.getString("TongTien");
                        String traTien = PayObject.getString("TrangThai");
                        Thongtinoder thongtinoder = new Thongtinoder();
                        thongtinoder.setMaOder(Integer.parseInt(MaOder));
                        thongtinoder.setMaBn(new String(MaBn));

                        thongtinoder.setTrangThai(TrangThai);
                        thongtinoder.setTongTien(Integer.parseInt(Tongtien));
                        thongtinoder.setTratien(Integer.parseInt(traTien));
                        listUnpaid.add(thongtinoder);
                    }
                } else {
                    Log.d("Error: ", "Failed to fetch data. Success is not 1.");
                }

            } catch (JSONException e) {
                Log.d("Error: ", e.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            unpaid1.notifyDataSetChanged();
        }

        public String readJsonOnline(String linkUrl) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(linkUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();
            } catch (Exception ex) {
                Log.d("Error: ", ex.toString());
            }
            return null;
        }
    }

    public void btnxemdanhsachban(int MaOder) {
        // Tạo một Intent và truyền tham số maOder
        Intent intent = new Intent(getApplicationContext(), Menu_pay.class);
        intent.putExtra("MaOder", MaOder);
        startActivity(intent);
    }
    public void Dangnhap(final String MaOder , String Matkhau) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Thongtinoder user = new Thongtinoder();
        user.setMaOder(Integer.parseInt(MaOder));
        user.setTrangThai(Integer.parseInt(Matkhau));

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THANHTOAN);
        serverRequest.setThongtinoder(user);
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
                Log.d(Constants.TAG, "Failed"+ t.getMessage());

            }
        });

    }
    public void Suatrangthaiban(final String MaBn , String Trangthai) {
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
                if (response1.getResult().equals(Constants.SUCCESS)){

                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed"+ t.getMessage());

            }
        });

    }


}