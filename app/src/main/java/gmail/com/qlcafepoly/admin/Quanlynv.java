package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Quanlynv extends AppCompatActivity {
    private List<User> lsuListNhanvien = new ArrayList<>();
    private Nhanvienht adepter;
    private ListView lshienthinhanvien;
    private String urllink = "http://192.168.1.7:8080/duantotnghiep/get_all_nhanvien.php";

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlynv);

        lshienthinhanvien = findViewById(R.id.lsHienThiNhanVien);
        adepter = new Nhanvienht(Quanlynv.this,lsuListNhanvien);
        lshienthinhanvien.setAdapter(adepter);

        pd = new ProgressDialog(Quanlynv.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu nhân viên...");
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
                    JSONArray jsonArraynhanvien = jsonObject.getJSONArray("nhanvien");
                    Log.d("//=====size===", jsonArraynhanvien.length() + "");

                    for (int i = 0; i < jsonArraynhanvien.length(); i++) {
                        JSONObject nhanvienObject = jsonArraynhanvien.getJSONObject(i);
                        Log.d("MaNv", nhanvienObject.getString("MaNv"));
                        Log.d("TenNv", nhanvienObject.getString("TenNv"));
                        Log.d("TenDn", nhanvienObject.getString("TenDn"));
                        Log.d("Matkhau", nhanvienObject.getString("Matkhau"));
                        Log.d("Sdt", nhanvienObject.getString("Sdt"));
                        Log.d("Diachi", nhanvienObject.getString("Diachi"));
                        Log.d("Chucvu", nhanvienObject.getString("Chucvu"));

                        String MaNv = nhanvienObject.getString("MaNv");
                        String TenNv = nhanvienObject.getString("TenNv");
                        String TenDn = nhanvienObject.getString("TenDn");
                        String Matkhau = nhanvienObject.getString("Matkhau");
                        String Sdt = nhanvienObject.getString("Sdt");
                        String Diachi = nhanvienObject.getString("Diachi");
                        String Chucvu = nhanvienObject.getString("Chucvu");

                        User user = new User();
                        user.setMaNv(MaNv);
                        user.setTenNv(TenNv);
                        user.setTenDn(TenDn);
                        user.setMatkhau(Matkhau);
                        user.setSdt(Sdt);
                        user.setDiachi(Diachi);
                        user.setChucvu(Integer.parseInt(Chucvu));
                        lsuListNhanvien.add(user);
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


    public void themnhanvien(View view){
        Intent intent = new Intent(Quanlynv.this, ThemNhanVien.class);
        startActivity(intent);
    }
    public void backNhanVien(View view){
        finish();
    }
}