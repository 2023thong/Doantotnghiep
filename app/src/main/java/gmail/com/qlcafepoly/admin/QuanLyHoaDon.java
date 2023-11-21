package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Menu_payFragment;
import gmail.com.qlcafepoly.nhanvien.PayFragment;
import gmail.com.qlcafepoly.nhanvien.Pay1;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;
import gmail.com.qlcafepoly.nhanvien.UnpaidFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuanLyHoaDon extends AppCompatActivity {
    private List<Thongtinoder> listPay = new ArrayList<>();
    private QLHD1 qlhd1;
    private ImageView imageView;
    private TextView tvTongBill;
    private ListView lvQLHD;
    private String urllink = "http://172.16.55.122:8080/duantotnghiep/oder.php";
    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hoa_don);
        imageView = findViewById(R.id.img_Douong);
        tvTongBill = findViewById(R.id.tvTongBill);
        lvQLHD = findViewById(R.id.lvQLHD);
        qlhd1 = new QLHD1(QuanLyHoaDon.this, listPay);
        lvQLHD.setAdapter(qlhd1);


        pd = new ProgressDialog(QuanLyHoaDon.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new QuanLyHoaDon.MyAsyncTask().execute(urllink);
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
                        String MaOder = PayObject.getString("MaOder");
                        String MaBn = PayObject.getString("MaBn");

                        int TrangThai = PayObject.getInt("TrangThai");
                        String Tongtien = PayObject.getString("TongTien");
                        String traTien = PayObject.getString("TrangThai");

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            qlhd1.notifyDataSetChanged();
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
        Intent intent = new Intent(getApplicationContext(), XemQLHD.class);
        intent.putExtra("MaOder", MaOder);
        startActivity(intent);
    }
    public void backHoaDon (View view){
        finish();
    }
}