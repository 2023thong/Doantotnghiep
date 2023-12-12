package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import java.text.DecimalFormat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class PayFragment extends Fragment {
    private List<Thongtinoder> listPay = new ArrayList<>();
    private Pay1 pay1;

    private ImageView imageView;
    private Button btnxemdanhsachban;
    private ListView lv_listpay;

    private String urllink = BASE_URL+ "duantotnghiep/oder.php";

    private ProgressDialog pd;


    @SuppressLint("MissingInflatedId")

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_pay, container, false);
        imageView = view.findViewById(R.id.img_Douong);
        lv_listpay = view.findViewById(R.id.lv_listpay);
        btnxemdanhsachban = view.findViewById(R.id.btnxemdanhsachban);

        pay1 = new Pay1(getActivity(), listPay);
        lv_listpay.setAdapter(pay1);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new MyAsyncTask().execute(urllink);

        return view;
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
                    JSONArray jsonArrayPay = jsonObject.getJSONArray("oder");
                    Log.d("//=====size=====", jsonArrayPay.length() + "");
                    for (int i = 0; i < jsonArrayPay.length(); i++) {
                        JSONObject PayObject = jsonArrayPay.getJSONObject(i);
                        Log.d("MaOder", PayObject.getString("MaOder"));
                        Log.d("MaBn", PayObject.getString("MaBn"));
                        Log.d("TongTien", PayObject.getString("TongTien"));
                        Log.d("Ngay", PayObject.getString("Ngay"));


                        String MaOder = PayObject.getString("MaOder");
                        String MaBn = PayObject.getString("MaBn");
                        int TrangThai = PayObject.getInt("TrangThai");
                        String Tongtien = PayObject.getString("TongTien");
                        String traTien = PayObject.getString("TrangThai");
                        String ngay = PayObject.getString("Ngay");


                        // Áp dụng định dạng giá tiền
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0.###");
                        String formattedTongtien = decimalFormat.format(Double.parseDouble(Tongtien));

                        Thongtinoder thongtinoder = new Thongtinoder();


                        thongtinoder.setMaOder(Integer.parseInt(MaOder));
                        thongtinoder.setMaBn(new String(MaBn));
                        thongtinoder.setTrangThai(TrangThai);
                        thongtinoder.setTongTien(Integer.parseInt(Tongtien));
                        thongtinoder.setFormattedTongtien(formattedTongtien);
                        thongtinoder.setTratien(Integer.parseInt(traTien));
                        thongtinoder.setNgay(ngay);
                        listPay.add(thongtinoder);
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
        public void btnxemdanhsachban(int MaOder) {
            // Tạo một Intent và truyền tham số maOder
            Intent intent = new Intent(getActivity(), Menu_pay.class);
            intent.putExtra("MaOder", MaOder);
            startActivity(intent);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pay1.notifyDataSetChanged();
                }
            });
        }



//        public void btnxemdanhsachban() {
//            Intent intent = new Intent(getActivity(), Menu_pay.class);
//            startActivity(intent);
//        }

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

//    public void btnxemdanhsachban(int MaOder) {
//        // Tạo một Intent và truyền tham số maOder
//        Intent intent = new Intent(getApplicationContext(), Menu_payFragment.class);
//        intent.putExtra("MaOder", MaOder);
//        startActivity(intent);
//    }

//    private Context getApplicationContext() {
//        return null;
//    }

//    public void Dangnhap(final String MaOder , String Matkhau) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
//        Thongtinoder user = new Thongtinoder();
//        user.setMaOder(Integer.parseInt(MaOder));
//        user.setTrangThai(Integer.parseInt(Matkhau));
//
//        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
//        serverRequest.setOperation(Constants.THANHTOAN);
//        serverRequest.setThongtinoder(user);
//        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
//
//
//        responseCall.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse response1 = response.body();
//                if (response1.getResult().equals(Constants.SUCCESS)){
//
//                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Log.d(Constants.TAG, "Failed"+ t.getMessage());
//
//            }
//        });
//    }


}