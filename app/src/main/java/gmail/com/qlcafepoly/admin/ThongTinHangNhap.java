package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

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

import gmail.com.qlcafepoly.R;

public class ThongTinHangNhap extends AppCompatActivity {
    private List<User1> lsuList = new ArrayList<>();
    private Hanghoaht adepter;
    private ImageView imageView;
    private ListView lshienthi;
    private String urllink = "http://192.168.1.33:/learn-login-register/get_all_product.php";

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_hang_nhap);
        imageView = findViewById(R.id.imageView5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lshienthi = findViewById(R.id.lsHienthi);

        adepter = new Hanghoaht(ThongTinHangNhap.this,lsuList);
        lshienthi.setAdapter(adepter);

        pd = new ProgressDialog(ThongTinHangNhap.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);



        new MyAsyncTask().execute(urllink);
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
                    JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("hanghoa");
                    Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                    for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                        JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                        Log.d("MaHH", nhanvienObject.getString("MaHH"));
                        Log.d("MaNcc", nhanvienObject.getString("MaNcc"));
                        Log.d("TenHh", nhanvienObject.getString("TenHh"));
                        Log.d("GiaSp", nhanvienObject.getString("GiaSp"));

                        String MaHH = nhanvienObject.getString("MaHH");
                        String MaNcc = nhanvienObject.getString("MaNcc");
                        String TenHh = nhanvienObject.getString("TenHh");
                        String GiaSp = nhanvienObject.getString("GiaSp");

                        User1 user1 = new User1();
                        user1.setMaHH(MaHH);
                        user1.setMaNcc(MaNcc);
                        user1.setTenHh(TenHh);
                        user1.setGiaSp(GiaSp);
                        lsuList.add(user1);
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


}