package gmail.com.qlcafepoly.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User1;

public class SuaOder extends AppCompatActivity {
    private OderHienthi adepteroder;
    private Hienthisua adepteroder1;
    private List<Menu> odermenu1 = new ArrayList<>();
    private ListView lshienthimenu;
    private List<Menu> odermenu = new ArrayList<>();
    private String urllink = "http://192.168.1.106:8080/duantotnghiep/get_all_menu.php";
    private String urllink1 = "http://192.168.1.106:8080/duantotnghiep/laydulieuchitietoder.php";

    private List<Menu> selectedMenus = new ArrayList<>();
    private ListView lshienthioder;
    private List<Menu> menuList = new ArrayList<>();
    private List<Menu> suaoder = new ArrayList<>();

    private String selectedMaoder;
    private Menu selectedMenu;



    private int soluongDefault = 1;

    private int totalAmount = 0;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_oder);

        lshienthimenu = findViewById(R.id.lshienmnsua);
        adepteroder = new OderHienthi(SuaOder.this, odermenu);
        lshienthimenu.setAdapter(adepteroder);

        lshienthioder = findViewById(R.id.lshienodersua);
        List<Menu> filteredList = filterMenuByMaoder(menuList, selectedMaoder);
        adepteroder1 = new Hienthisua(SuaOder.this, filteredList, selectedMaoder);
        lshienthioder.setAdapter(adepteroder1);



        Intent intent1 = getIntent();
        selectedMaoder = intent1.getStringExtra("MaOderoder");




        Intent intent = getIntent();
        String Maoder1 = intent.getStringExtra("MaOderoder");
        String Mabn = intent.getStringExtra("MaBnoder");
        String Tongtien1 = intent.getStringExtra("TongTienoder");



        TextView tvmaoder = findViewById(R.id.tvMaodersua);
        TextView tvmabn = findViewById(R.id.tvMabnsua);
        TextView tvtongtien = findViewById(R.id.TvTongtiensua);

        tvmaoder.setText(Maoder1);
        tvmabn.setText(Mabn);
        tvtongtien.setText(Tongtien1);





        pd = new ProgressDialog(SuaOder.this);
        pd.setMessage("Đang tải dữ liệu menu...");
        pd.setCancelable(false);
        new MyAsyncTask().execute(urllink);
        new MyAsyncTask1().execute(urllink1);


        lshienthimenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu selectedMenu = odermenu.get(position);

                if (!selectedMenus.contains(selectedMenu)) {
                    selectedMenu.setSoluong(soluongDefault);

                    selectedMenus.add(selectedMenu);
                    updateListViewAbove(selectedMenu);


                }
            }
        });
    }
    private List<Menu> filterMenuByMaoder(List<Menu> menuList, String selectedMaoder) {
        List<Menu> filteredList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (String.valueOf(menu.getMaOder()).equals(selectedMaoder)) {
                filteredList.add(menu);
            }
        }
        return filteredList;
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


                        String MaMn = menuObject.getString("MaMn");
                        String TenDu = menuObject.getString("TenDu");
                        String Giatien = menuObject.getString("Giatien");

                        SharedPreferences sharedPreferences = getSharedPreferences("menu1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("MaMn", MaMn);
                        editor.apply();



                        Menu menu = new Menu();
                        menu.setMaMn(MaMn);
                        menu.setTenDu(TenDu);
                        menu.setGiatien(Integer.parseInt(Giatien));
                        odermenu.add(menu);
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
    private class MyAsyncTask1 extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Đang tải dữ liệu...");
            pd.setCancelable(false);
            pd.show();
            adepteroder1.notifyDataSetChanged();
        }
        @Override

        protected String doInBackground(String... strings) {
            try {
                String strJson = readJsonOnline(strings[0]);
                Log.d("//====", strJson);

                JSONObject jsonObject = new JSONObject(strJson);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    if (jsonObject.has("thongtinoder")) {
                        JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("thongtinoder");
                        Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                        for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                            JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                            Log.d("MaOder", nhanvienObject.getString("MaOder"));
                            Log.d("TenDu", nhanvienObject.getString("TenDu"));
                            Log.d("Soluong", nhanvienObject.getString("Soluong"));
                            Log.d("Giatien", nhanvienObject.getString("Giatien"));

                            String MaOder = nhanvienObject.getString("MaOder");
                            String TenDu = nhanvienObject.getString("TenDu");
                            String Soluong = nhanvienObject.getString("Soluong");
                            String Giatien = nhanvienObject.getString("Giatien");

                            Menu user1 = new Menu();
                            user1.setMaOder(Integer.parseInt(MaOder));
                            user1.setTenDu(TenDu);
                            user1.setSoluong(Integer.parseInt(Soluong));
                            user1.setGiatien(Integer.parseInt(Giatien));

                            menuList.add(user1);
                        }
                    } else {
                        Log.d("Error: ", "No value for nhacungcap");
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
            adepteroder1.notifyDataSetChanged();


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

    private class CustomAdapter extends ArrayAdapter<Menu> {
        public CustomAdapter(Context context, List<Menu> menuList) {
            super(context, 0, menuList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Menu menu = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemoder, parent, false);
            }

            TextView customTextView = convertView.findViewById(R.id.tvTenoder1);
            TextView customTextView1 = convertView.findViewById(R.id.tvGiaDuOder1);
            ImageView imageXoa = convertView.findViewById(R.id.imgXoa1);
            ImageView imgTang = convertView.findViewById(R.id.btnTang);
            final TextView sl = convertView.findViewById(R.id.tvsl);
            ImageView imgGiam = convertView.findViewById(R.id.imgGiam);



            customTextView.setText(menu.getTenDu());
            customTextView1.setText(String.valueOf(menu.getGiatien()));

            sl.setText(String.valueOf(menu.getSoluong()));

            if (menu == selectedMenu) {
                sl.setText(String.valueOf(soluongDefault));
            } else {
                sl.setText(String.valueOf(menu.getSoluong()));
            }

            imgTang.setTag(position);
            sl.setTag(position);

            imgTang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Menu selectedMenu = getItem(position);

                    int currentQuantity = selectedMenu.getSoluong();
                    currentQuantity++;
                    selectedMenu.setSoluong(currentQuantity);



                    int giabd = (selectedMenu.getGiatien()); // Lấy giá tiền ban đầu
                    int giamoi = giabd * currentQuantity; // Tính giá tiền mới dựa trên số lượng mới
                    selectedMenu.setGiatientd(giamoi); // Cập nhật giá tiền dựa trên số lượng mới
                    sl.setText(String.valueOf(currentQuantity));
                    customTextView1.setText(String.valueOf(giamoi));
                    updateTotalAmount();


                }
            });
            imgGiam.setTag(position);
            sl.setTag(position);

            imgGiam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Menu selectedMenu = getItem(position);

                    int currentQuantity = selectedMenu.getSoluong();

                    // Giảm số lượng
                    if (currentQuantity > 1) {
                        currentQuantity--;
                        selectedMenu.setSoluong(currentQuantity);
                    }

                    int giabd = selectedMenu.getGiatien(); // Lấy giá tiền ban đầu
                    int giamoi = giabd * currentQuantity; // Tính giá tiền mới d
                    selectedMenu.setGiatientd(giamoi); // Cập nhật giá tiền dựa trên số lượng mới
                    sl.setText(String.valueOf(currentQuantity));
                    customTextView1.setText(String.valueOf(giamoi));
                    updateTotalAmount();



                }
            });


            imageXoa.setTag(position);
            imageXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position1 = (int) view.getTag();
                    Menu itemToRemove = odermenu1.get(position1);

                    odermenu1.remove(position1);
                    selectedMenus.clear();


                    updateTotalAmount();

                    // Cập nhật danh sách hiển thị
                    SuaOder.CustomAdapter customAdapter = (SuaOder.CustomAdapter) lshienthioder.getAdapter();
                    customAdapter.notifyDataSetChanged();
                }
            });




            sl.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (selectedMenu != null) {
                        int newQuantity = Integer.parseInt(charSequence.toString());
                        selectedMenu.setSoluong(newQuantity);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            return convertView;
        }
    }

    public String check() {
        CheckBox box = findViewById(R.id.checkBox);
        String value;

        if (box.isChecked()) {
            value = String.valueOf(1);
            // Thực hiện các tác vụ khác dựa trên giá trị 1
        } else {
            value = String.valueOf(2);
            // Thực hiện các tác vụ khác dựa trên giá trị 2
        }

        return value;
    }


    private void updateListViewAbove(Menu menu) {
        odermenu1.add(menu);
        SuaOder.CustomAdapter customAdapter = new SuaOder.CustomAdapter(this, odermenu1);
        lshienthioder.setAdapter(customAdapter);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        // Tổng tiền từ intent
        Intent intent = getIntent();
        String Tongtien1 = intent.getStringExtra("TongTienoder");

        // Chắc chắn rằng Tongtien1 không phải là null
        if (Tongtien1 == null) {
            Tongtien1 = "0";
        }

        // Parse giá trị từ string sang int
        int tongTien1 = Integer.parseInt(Tongtien1);

        // Tổng tiền từ ListView
        int totalAmountFromListView = 0;
        for (Menu menu : odermenu1) {
            totalAmountFromListView += menu.calculateTotalPrice();
        }

        // Tổng tiền cuối cùng
        int tong = totalAmountFromListView + tongTien1;

        // Hiển thị tổng tiền cuối cùng trong TextView
        TextView totalAmountTextView = findViewById(R.id.TvTongtiensua);
        totalAmountTextView.setText(String.valueOf(tong));
    }



}