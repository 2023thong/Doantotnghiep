package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

public class ThemNhaCungCap extends AppCompatActivity {

    private EditText edMancc, edTenncc, edDiachi, edsdt;
    private Button imgThem;

    private List<User2> lsuList1 = new ArrayList<>();
    private Themncc adepter;
    private ImageView view;

    private ListView lshienthi;
    private ProgressDialog pd;
    private String urllink = "http://192.168.1.39:8080/duantotnghiep/thu.php";




    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_cung_cap);

        view = findViewById(R.id.backthoatncc);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView capnhat  = findViewById(R.id.capnhatncc);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemNhaCungCap.this, ThemNhaCungCap.class);
                startActivity(intent);
                finish();
            }
        });




        edTenncc = findViewById(R.id.edTencct);
        edDiachi = findViewById(R.id.edDiachit);
        edsdt = findViewById(R.id.edSdtt);

        lshienthi = findViewById(R.id.lvhiethincc);
        adepter = new Themncc(ThemNhaCungCap.this, lsuList1);
        lshienthi.setAdapter(adepter);

        pd = new ProgressDialog(ThemNhaCungCap.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);



        new ThemNhaCungCap.MyAsyncTask().execute(urllink);


        imgThem = findViewById(R.id.btnThemt);
        imgThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenncc = edTenncc.getText().toString();
                String diachi = edDiachi.getText().toString();
                String sdt = edsdt.getText().toString();

                Themnhacungcap(tenncc, diachi, sdt);


            }
        });
    }

    public void Themnhacungcap(String TenNcc, String Diachi, String Sdt) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User2 user2 = new User2();
        user2.setTenNcc(TenNcc);
        user2.setDiachi(Diachi);
        user2.setSdt(Sdt);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THEMNHACUNGCAP);
        serverRequest.setUser2(user2);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ThemNhaCungCap.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối đến CSDL khi Fragment bị hủy

    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
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
                    JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("nhacungcap");
                    Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                    for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                        JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);

                        Log.d("MaNcc", nhanvienObject.getString("MaNcc"));
                        Log.d("TenNcc", nhanvienObject.getString("TenNcc"));
                        Log.d("Diachi", nhanvienObject.getString("Diachi"));
                        Log.d("Sdt", nhanvienObject.getString("Sdt"));



                        String MaNcc = nhanvienObject.getString("MaNcc");
                        String TenNcc = nhanvienObject.getString("TenNcc");
                        String Diachi = nhanvienObject.getString("Diachi");
                        String Sdt = nhanvienObject.getString("Sdt");




                        User2 user1 = new User2();

                        user1.setMaNcc(MaNcc);
                        user1.setTenNcc(TenNcc);
                        user1.setDiachi(Diachi);
                        user1.setSdt(Sdt);

                        lsuList1.add(user1);
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
            adepter.notifyDataSetChanged();



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
    public void deleteNcc(final String MaNcc) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User2 user2 = new User2();
        user2.setMaNcc(MaNcc);  // Set the item ID to be deleted
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.XOANHACUNGCAP);
        serverRequest.setUser2(user2);

        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    updateProductList();
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi" + response1.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Tag", "" + response1.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateProductList() {
        lsuList1.clear(); // Xóa danh sách hiện tại
        new ThemNhaCungCap.MyAsyncTask().execute(urllink); // Tải danh sách mới
    }


}