package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

public class Trangcoffee extends AppCompatActivity {
    private List<Menu> lsuListMenu = new ArrayList<>();
    private Menuht adepter;
    private ListView lshienthimenu;
    private String urllink = "http://192.168.1.22:8080/duantotnghiep/get_all_menu.php";

    private ProgressDialog pd;
    private ImageView btnThemcofe, btnthoatcofe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcoffee);
        lshienthimenu = findViewById(R.id.lscofe);
        adepter = new Menuht(Trangcoffee.this, lsuListMenu);
        lshienthimenu.setAdapter(adepter);
        btnThemcofe = findViewById(R.id.btnthemcofe);
        btnthoatcofe = findViewById(R.id.imgbackcofee);

        pd = new ProgressDialog(Trangcoffee.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu menu...");
        pd.setCancelable(false);


        new MyAsyncTask().execute(urllink);
        btnThemcofe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trangcoffee.this,ThemDoUong.class);
                startActivity(intent);
            }
        });
        btnthoatcofe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Trangcoffee.this,QuanLyDoUong.class);
                startActivity(intent);
            }
        });


    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
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
                        Log.d("MaMn", menuObject.getString("MaMn"));
                        Log.d("TenLh", menuObject.getString("TenLh"));
                        Log.d("Giatien", menuObject.getString("Giatien"));

                        String MaNn = menuObject.getString("MaMn");
                        String TenLh = menuObject.getString("TenLh");
                        String Giatien = menuObject.getString("Giatien");

                        Menu menu = new Menu();
                        menu.setMaMn(MaNn);
                        menu.setTenLh(TenLh);
                        menu.setGiatien(Integer.parseInt(Giatien));
                        lsuListMenu.add(menu);
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

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd.setMessage("Đang tải dữ liệu...");
//            pd.setCancelable(false);
//            pd.show();
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

//        public void themdouong(View view) {
//            Intent intent = new Intent(Trangcoffee.this, ThemDoUong.class);
//            startActivity(intent);
//        }
//
//        public void backcofee(View view) {
//            Intent intent = new Intent(Trangcoffee.this, QuanLyDoUong.class);
//            startActivity(intent);
//        }
    }
}