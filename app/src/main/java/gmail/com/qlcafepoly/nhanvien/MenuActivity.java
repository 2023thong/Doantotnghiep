package gmail.com.qlcafepoly.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import gmail.com.qlcafepoly.Menu.DuUong;
import gmail.com.qlcafepoly.Menu.order;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.model.Menu;

public class MenuActivity extends AppCompatActivity {
    private List<Menu> menuList1 = new ArrayList<>();
    private MenuDU menudu;
    private ImageView imageView;
    private ListView lsMenuSql;
    private String urllink = "http://192.168.1.102:/duantotnghiep/thongtintk.php";

    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imageView = findViewById(R.id.imgnuoc);
        lsMenuSql = findViewById(R.id.lsmenudu);
        menudu = new MenuDU(MenuActivity.this,menuList1);
        lsMenuSql.setAdapter(menudu);

        pd = new ProgressDialog(MenuActivity.this); // Khởi tạo ProgressDialog ở đây
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
                    JSONArray jsonArraymenu = jsonObject.getJSONArray("menu");
                    Log.d("//=====size===", jsonArraymenu.length() + "");

                    for (int i = 0; i < jsonArraymenu.length(); i++) {
                        JSONObject menuObject = jsonArraymenu.getJSONObject(i);
//                        Log.d("MaMn", menuObject.getString("MaMn"));
                        Log.d("TenLh", menuObject.getString("TenLh"));
                        Log.d("Giatien", menuObject.getString("Giatien"));


//                        String MaMn = menuObject.getString("MaMn");
                        String TenLh = menuObject.getString("TenLh");
                        String Giatien = menuObject.getString("Giatien");


                        Menu menu = new Menu();
                        menu.setTenLh(TenLh);
                        menu.setGiatien(Integer.parseInt(Giatien));
                        menuList1.add(menu);

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
            menudu.notifyDataSetChanged();
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