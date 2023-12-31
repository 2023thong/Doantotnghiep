package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class DanhSachDoUong extends AppCompatActivity {
    private List<Menu> originalList = new ArrayList<>();
    private List<Menu> lsuListMenu = new ArrayList<>();
    private Menuht adepter;
    private ListView lshienthimenu;
    private String urllink = BASE_URL +"duantotnghiep/get_all_menu.php";



    private ProgressDialog pd;
    private EditText edFindDU;
    private Button btnThemDU;
    private View btnBackDU;
    private ImageView view1,icLoadMenu;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_do_uong);
        lshienthimenu = findViewById(R.id.lvDSDU);
        adepter = new Menuht(DanhSachDoUong.this, lsuListMenu);
        lshienthimenu.setAdapter(adepter);
        btnThemDU = findViewById(R.id.btnThemDU);
        btnBackDU = findViewById(R.id.backDSDU);
        edFindDU = findViewById(R.id.edFindDU);
        edFindDU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        icLoadMenu = findViewById(R.id.icLoadMenu);
        icLoadMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reload the activity
                finish();
                startActivity(getIntent());
            }
        });

        pd = new ProgressDialog(DanhSachDoUong.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu menu...");
        pd.setCancelable(false);
        new MyAsyncTask().execute(urllink);
        btnThemDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachDoUong.this, ThemDoUong.class);
                startActivity(intent);
            }
        });
        btnBackDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

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
                        Log.d("TenDu", menuObject.getString("TenDu"));
                        Log.d("Giatien", menuObject.getString("Giatien"));
                        Log.d("TenLh", menuObject.getString("TenLh"));

                        String MaNn = menuObject.getString("MaMn");
                        String TenDu = menuObject.getString("TenDu");
                        String Giatien = menuObject.getString("Giatien");
                        String TenLh = menuObject.getString("TenLh");

                        Menu menu = new Menu();
                        menu.setMaMn(MaNn);
                        menu.setTenDu(TenDu);
                        menu.setGiatien(Integer.parseInt(Giatien));
                        menu.setTenLh(TenLh);
                        lsuListMenu.add(menu);
                    }
                } else {
                    Log.d("Error: ", "Failed to fetch data. Success is not 1.");
                }
                originalList.clear();
                originalList.addAll(lsuListMenu);
            } catch (JSONException e) {
                Log.d("Error: ", e.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
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
    private void filter(String text) {
        lsuListMenu.clear();
        if (text.isEmpty()) {
            // If the search query is empty, show the original list
            lsuListMenu.addAll(originalList);
        } else {
            // Otherwise, filter the list based on MaNv
            String normalizedText = normalizeText(text);
            for (Menu menu : originalList) {
                String normalizedMenuTenDu = normalizeText(menu.getTenDu());
                if (normalizedMenuTenDu.contains(normalizedText)) {
                    lsuListMenu.add(menu);
                }
            }
        }
        adepter.notifyDataSetChanged();
    }
    private String normalizeText(String text) {
        // Use Normalizer to remove diacritics
        String normalizedText = java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD);
        // Remove non-alphanumeric characters and convert to lowercase
        return normalizedText.replaceAll("[^\\p{ASCII}a-zA-Z0-9]", "").toLowerCase();
    }
}