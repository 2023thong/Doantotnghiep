package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.model.Ban;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OderDu extends AppCompatActivity {
    private List<Menu> odermenu = new ArrayList<>();
    private List<Menu> odermenu1 = new ArrayList<>();
    private List<Oder> oder2 = new ArrayList<>();
    private ListView lshienthimenu;
    private ListView lshienthioder;




    private OderHienthi adepteroder;
    private int soluongDefault = 1;

    private int totalAmount = 0;




    private Menu selectedMenu;

    private String urllink =  BASE_URL +"duantotnghiep/get_all_menu.php";

    private ProgressDialog pd;
    private List<Menu> selectedMenus = new ArrayList<>();
    private Menu menu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_du);

        lshienthimenu = findViewById(R.id.lshienmn);
        adepteroder = new OderHienthi(OderDu.this, odermenu);
        lshienthimenu.setAdapter(adepteroder);

        lshienthioder = findViewById(R.id.lshienoder);

        TextView Mabn  = findViewById(R.id.tvMabn);

        TextView Tongtien = findViewById(R.id.TvTongtien);


        Intent intent = getIntent();
        String maHH = intent.getStringExtra("MAODER");
        Mabn.setText(maHH);



        
        pd = new ProgressDialog(OderDu.this);
        pd.setMessage("Đang tải dữ liệu menu...");
        pd.setCancelable(false);
        new MyAsyncTask().execute(urllink);
//        new MyAsyncTask1().execute(urllink1);


        lshienthimenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu selectedMenu = odermenu.get(position);

                if (!selectedMenus.contains(selectedMenu)) {
                    selectedMenu.setSoluong(soluongDefault);
                    selectedMenu.getGiatien();
                    selectedMenus.add(selectedMenu);
                    updateListViewAbove(selectedMenu);

                }
            }
        });



        TextView oder = findViewById(R.id.tvOder);

        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedMenus.isEmpty()) {

                    String mabn = Mabn.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences("oder", Context.MODE_PRIVATE);
                    String maOder = sharedPreferences.getString("Maoder", ""); // The second parameter is the default value if the key is not found

                    TextView textView = findViewById(R.id.tvMaoder);
                    textView.setText(maOder);
                    String maoderd = textView.getText().toString();

                    String tendu;
                    String sl;
                    for (Menu selectedMenu : selectedMenus) {
                        tendu = selectedMenu.getTenDu();
                        sl = String.valueOf(selectedMenu.getSoluong());
                        String gia = String.valueOf(selectedMenu.getGiatien());
                        ThemOderchitiet(maoderd, tendu, sl, gia, mabn);
                    }

                    // Move the removal of "Maoder" outside the loop
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("Maoder");
                    editor.apply();




                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng bấm lưu trước khi Oder", Toast.LENGTH_SHORT).show();
                }

            }



    });
        TextView btnLuu = findViewById(R.id.btnLuu);
        final boolean[] isSaved = {false}; // Biến cờ để kiểm tra trạng thái đã lưu

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedMenus.isEmpty()) {
                    if (!isSaved[0]) {
                        String mabn = Mabn.getText().toString();
                        String tongtien = Tongtien.getText().toString();
                        String trangthai = check();

                        SharedPreferences sharedPreferences = getSharedPreferences("menu1", Context.MODE_PRIVATE);
                        String maOder1 = sharedPreferences.getString("MaMn", ""); // The second parameter is the default value if the key is not found

                        TextView Mamn = findViewById(R.id.tvMamn0);
                        Mamn.setText(maOder1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("MaMn");
                        editor.apply();

                        String menu = Mamn.getText().toString();

                        ThemOder(mabn, tongtien, menu, trangthai);
                         Trangthaibn(mabn, trangthai);

                        isSaved[0] = true;
                    } else {
                        // Thông báo nếu đã lưu 1 lần
                        Toast.makeText(getApplicationContext(), "Bạn đã lưu rồi. Bạn vui lòng oder", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn đồ uống trước khi Lưu", Toast.LENGTH_SHORT).show();
                }
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


                        String MaMn = menuObject.getString("MaMn");
                        String TenDu = menuObject.getString("TenDu");
                        String Giatien = menuObject.getString("Giatien");

                        SharedPreferences sharedPreferences = getSharedPreferences("menu1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("MaMn", MaMn);  // Replace "TenDn" with your key and TenDn with the value you want to store
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

            TextView mamn = convertView.findViewById(R.id.tvMaMn1);

            TextView customTextView = convertView.findViewById(R.id.tvTenoder1);
            TextView customTextView1 = convertView.findViewById(R.id.tvGiaDuOder1);
            ImageView imageXoa = convertView.findViewById(R.id.imgXoa1);
            ImageView imgTang = convertView.findViewById(R.id.btnTang);
            final TextView sl = convertView.findViewById(R.id.tvsl);
            ImageView imgGiam = convertView.findViewById(R.id.imgGiam);






//            mamn.setText(menu.getMaMn());
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

                    int giabd = selectedMenu.getGiatien(); // Lấy giá tiền ban đầu
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
                    CustomAdapter customAdapter = (CustomAdapter) lshienthioder.getAdapter();
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
        CustomAdapter customAdapter = new CustomAdapter(this, odermenu1);
        lshienthioder.setAdapter(customAdapter);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        int totalAmount = 0;

        for (Menu menu : odermenu1) {
            totalAmount += menu.calculateTotalPrice();
        }

        TextView totalAmountTextView = findViewById(R.id.TvTongtien);
        totalAmountTextView.setText(String.valueOf(totalAmount));
    }

    public void ThemOder(String MaBn, String TongTien, String MaMn, String TrangThai) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Oder oder1 = new Oder();
        oder1.setMaBn(MaBn);
        oder1.setTongTien(TongTien);
        oder1.setMaMn(MaMn);
        oder1.setTrangThai(TrangThai);
        Gson gson = new Gson();
        String jsonData = gson.toJson(oder1);
        Log.d("JSON oder", jsonData);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THEMODER);
        serverRequest.setOder1(oder1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    String maOder = response1.getMaOder();
                    SharedPreferences sharedPreferences = getSharedPreferences("oder", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Maoder", maOder);  // Replace "TenDn" with your key and TenDn with the value you want to store
                    editor.apply();  // or editor.commit() to save the data


                } else {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }
        });
    }
    public void ThemOderchitiet(String MaOder,String TenDu, String Soluong,String Giatien, String MaBn) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Oder oder1 = new Oder();
        oder1.setMaOder(MaOder);


        oder1.setTenDu(TenDu);
        oder1.setSoluong(Soluong);
        oder1.setGiatien(Giatien);
        oder1.setMaBn(MaBn);
        Gson gson = new Gson();
        String jsonData = gson.toJson(oder1);
        Log.d("JSON chitietoder", jsonData);


        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THEMODERCT);
        serverRequest.setOder1(oder1);


        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OderDu.this, Unpaid.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }

        });
    }
    public void Trangthaibn(String MaBn, String Trangthai) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Ban ban = new Ban();
        ban.setMaBn(MaBn);


        ban.setTrangthai(Trangthai);


        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUABAN);
        serverRequest.setBan(ban);


        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OderDu.this, Unpaid.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }

        });
    }
    public void SoluongKho(){

    }







}
