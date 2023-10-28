package gmail.com.qlcafepoly.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import gmail.com.qlcafepoly.model.Menu;

public class order extends AppCompatActivity {

    private List<Menu> menuList = new ArrayList<>();
    private DuUong duUong;
    private ImageView imageView;
    private ListView lsMenuSql;
    private String urllink = "http://192.168.1.118:/duantotnghiep/get_all_product.php";

    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        imageView = findViewById(R.id.imageView5);
        lsMenuSql = findViewById(R.id.lsmeuu);
        duUong = new DuUong(order.this,menuList);
        lsMenuSql.setAdapter(duUong);

        pd = new ProgressDialog(order.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);



        new order.MyAsyncTask().execute(urllink);
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
                    JSONArray jsonArraymenu = jsonObject.getJSONArray("menu");
                    Log.d("//=====size===", jsonArraymenu.length() + "");

                    for (int i = 0; i < jsonArraymenu.length(); i++) {
                        JSONObject menuObject = jsonArraymenu.getJSONObject(i);
//                        Log.d("MaMn", menuObject.getString("MaMn"));
                        Log.d("TenLh", menuObject.getString("TenLh"));
                        Log.d("GiaTien", menuObject.getString("GiaTien"));


//                        String MaMn = menuObject.getString("MaMn");
                        String TenLh = menuObject.getString("TenLh");
                        String GiaTien = menuObject.getString("GiaTien");


                        Menu menu = new Menu();
//                        menu.setMaMn(MaMn);
                        menu.setTenLh(TenLh);
                        menu.setGiatien(Integer.parseInt(GiaTien));
                        menuList.add(menu);

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
            duUong.notifyDataSetChanged();
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