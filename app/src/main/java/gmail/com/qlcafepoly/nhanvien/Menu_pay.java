package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

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
import androidx.appcompat.app.AppCompatActivity;
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

public class Menu_pay extends AppCompatActivity {
    private List<Menu1> listPay = new ArrayList<>();
    private PayDU payDU;

    private ImageView imageView;
    private ListView lvListOder;
    private String base_url = "http://192.168.1.173:8080/duantotnghiep/thongtinctoder.php";
    private String urllink = BASE_URL +"duantotnghiep/thongtinctoder.php?MaOder=-1";
    private ProgressDialog pd;
    private int MaOder = -1;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pay);

        Intent intent = getIntent();
        String maOderValue = intent.getStringExtra("Maoder1" );
        String tien1 = intent.getStringExtra("Tongtien");
        String bn = intent.getStringExtra("Mabn");
        String ngay = intent.getStringExtra("Ngay");
        TextView tongtien = findViewById(R.id.tvTongtien0);
        TextView tongtien1 = findViewById(R.id.tongtien2);
        TextView ngay1 = findViewById(R.id.tvNgay);
        TextView tenban = findViewById(R.id.tvMabn0);
        TextView quaylai = findViewById(R.id.btnQuaylaij);

        tongtien.setText(tien1);
        tongtien1.setText(tien1);
        tenban.setText(bn);
        ngay1.setText(ngay);

            try {
                int maOderInt = Integer.parseInt(maOderValue);
                if (maOderInt != -1) {
                    urllink = BASE_URL +"duantotnghiep/thongtinctoder.php?MaOder=" + maOderValue;
                }
                else {
                    Log.e("thu", "Invalid MaOder value: -1");
                }
            } catch (NumberFormatException e) {
                Log.e("thu", "Invalid MaOder format");
        }
            quaylai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        imageView = findViewById(R.id.img_Douong);
        lvListOder = findViewById(R.id.lv_listoder);
        payDU = new PayDU(Menu_pay.this, listPay);
        lvListOder.setAdapter(payDU);

        pd = new ProgressDialog(Menu_pay.this); // Khởi tạo ProgressDialog ở đây
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
                    JSONArray jsonArrayPay = jsonObject.getJSONArray("chitietoder");
                    Log.d("//=====size=====", jsonArrayPay.length() + "");

                    for (int i = 0; i < jsonArrayPay.length(); i++) {
                        JSONObject PayObject = jsonArrayPay.getJSONObject(i);

                        Log.d("TenDu", PayObject.getString("TenDu"));
                        Log.d("Giatien", PayObject.getString("Giatien"));
                        Log.d("Soluong", PayObject.getString("Soluong"));
                        Log.d("MaOder", PayObject.getString("MaOder"));

                        String TenDu = PayObject.getString("TenDu");
                        String Giatien = PayObject.getString("Giatien");
                        String Soluong = PayObject.getString("Soluong");

                        Menu1 menu1 = new Menu1();
                        menu1.setTenDu(TenDu);
                        menu1.setGiatien(Integer.parseInt(Giatien));
                        menu1.setSoluong(Integer.parseInt(Soluong));

                        listPay.add(menu1);
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
            payDU.notifyDataSetChanged();
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
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception e) {
                    Log.d("Error: ", e.toString());
                }
            }
            return null;
        }
    }
}