package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemThongTinDU extends AppCompatActivity {
    Bitmap bitmap;
    private String TenLhFromIntent;
    private ImageView icanhmenu;
    Button btnBack,btnSuaDU,btnXoaDU;
    private TextView btnanhmenu,edMaMN;
    EditText edTenLH,edGiaTien;
    private Spinner SpTenLh;
    private List<User1> lsuList = new ArrayList<User1>();
    private String urllink1 =BASE_URL + "duantotnghiep/loaihang.php";
    private ProgressDialog pd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_thong_tin_du);
        edMaMN = findViewById(R.id.edMaDU);
        edTenLH =findViewById(R.id.edTenDU);
        edGiaTien =findViewById(R.id.edGiaDU);
        SpTenLh = findViewById(R.id.SpTenLh);
        btnSuaDU = findViewById(R.id.btnSuaDU);
        btnBack = findViewById(R.id.btnBack);
        btnXoaDU = findViewById(R.id.btnXoaDU);
        btnSuaDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mamn = edMaMN.getText().toString();
                String tendu = edTenLH.getText().toString();
                String giatien = edGiaTien.getText().toString();
                String tenlh = SpTenLh.getSelectedItem().toString();

                // Validate Giatien
                if (!isNumeric(giatien)) {
                    Toast.makeText(ItemThongTinDU.this, "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
                    return; // Do not proceed if Giatien is not a number.
                }

                EditMenu(mamn, tendu, giatien, tenlh);
            }
        });
        pd = new ProgressDialog(ItemThongTinDU.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new ItemThongTinDU.MyAsyncTask().execute(urllink1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String MaMn = intent.getStringExtra("DULIEUDU");
        String TenDu = intent.getStringExtra("DULIEUDU_TenDu");
        String Giatien = intent.getStringExtra("DULIEUDU_Giatien");
        TenLhFromIntent = intent.getStringExtra("DULIEUDU_TenLh");

        TextView edMaMn = findViewById(R.id.edMaDU);
        EditText edTenLh = findViewById(R.id.edTenDU);
        EditText edGiatien = findViewById(R.id.edGiaDU);
        Spinner SpTenLh = findViewById(R.id.SpTenLh);
        edMaMn.setText(MaMn);
        edTenLh.setText(TenDu);
        edGiatien.setText(Giatien);
        btnXoaDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
        icanhmenu = findViewById(R.id.icmanhmenu);
        btnanhmenu = findViewById(R.id.btnanhmenu);
        //hienanh
        // Gửi tên tài khoản lên máy chủ để lấy hình ảnh
        String imageUrl = BASE_URL + "duantotnghiep/layhinhanhmenu.php?MaMn=" +MaMn;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        icanhmenu.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItemThongTinDU.this, "Thêm Avatar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(imageRequest);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        icanhmenu.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }

                }
            }
        });
        icanhmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });
        btnanhmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaMn = edMaMn.getText().toString();
                Log.d("/tendu" , MaMn);
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64img = Base64.encodeToString(bytes, Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url =BASE_URL +"duantotnghiep/databasemenu.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(ItemThongTinDU.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ItemThongTinDU.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("image", base64img);
                            paramV.put("MaMn", MaMn); // Bổ sung thông tin tên khách hàng
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(ItemThongTinDU.this, "Chọn ảnh thay đổi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    if (jsonObject.has("loaihang")) {
                        JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("loaihang");
                        Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                        for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                            JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                            Log.d("TenLh", nhanvienObject.getString("TenLh"));

                            String tenLh = nhanvienObject.getString("TenLh");
                            User1 user1 = new User1();
                            user1.setTenLh(tenLh);
                            lsuList.add(user1);
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
                Log.d("loi: ", e.getMessage());

                e.getStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            populateSpinnerWithMaLh();

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
    private void populateSpinnerWithMaLh() {
        List<String> maLhValues = new ArrayList<>();
        for (User1 user : lsuList) {
            String maLh = user.getTenLh();
            maLhValues.add(maLh);
        }

        ArrayAdapter<String> spinnerMaLhAdapter = new ArrayAdapter<>(ItemThongTinDU.this, android.R.layout.simple_spinner_item, maLhValues);
        spinnerMaLhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpTenLh.setAdapter(spinnerMaLhAdapter);

        // Xét điều kiện và chọn mục tương ứng trong Spinner
        if (!maLhValues.isEmpty()) {
            int position = maLhValues.indexOf(TenLhFromIntent);
            if (position != -1) {
                SpTenLh.setSelection(position);
            }
        }
    }
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Allows integers and decimals
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Người dùng đã xác nhận xóa, thực hiện hành động xóa ở đây
                String mamn = edMaMN.getText().toString();
                deleteMenu(mamn);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Người dùng đã chọn không, đóng dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void EditMenu(String MaMn , String TenDu, String Giatien,String TenLh) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Menu menu = new Menu();
        menu.setMaMn(MaMn);
        menu.setTenDu(TenDu);
        menu.setGiatien(Integer.parseInt(String.valueOf(Giatien)));
        menu.setGiatien(Integer.parseInt(Giatien));
        menu.setTenLh(TenLh);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUAMENU);
        serverRequest.setMenu(menu);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ItemThongTinDU.this, DanhSachDoUong.class);
                    // Use FLAG_ACTIVITY_CLEAR_TOP to clear the activity stack
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }
    public void deleteMenu( final String MaMn) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Menu menu = new Menu();
        menu.setMaMn(MaMn);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.XOAMENU);
        serverRequest.setMenu(menu);

        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    // Sau khi xóa thành công, quay về màn hình Quanlynv hoặc thực hiện các xử lý khác tùy ý.
                    Intent intent = new Intent(ItemThongTinDU.this, DanhSachDoUong.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemThongTinDU.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");
            }
        });
    }
}