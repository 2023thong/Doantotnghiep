package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class ThemDoUong extends AppCompatActivity{
    Bitmap bitmap;
    private String hinhanh;
    private List<String> tenLhValues = new ArrayList<>();
    private ArrayAdapter<String> spinnerTenLhAdapter;
    private List<User1> lsuList = new ArrayList<User1>();
    private EditText edMaMn, edTenLh, edGiatien;
    private ImageView icthemanhmenu1;
    private Button btnThemmenu, btnxemmenu;
    private View backThemDU;
    private User1 loaiHang;
    private Spinner SpnTenLh;
    private String urllink1 =BASE_URL + "duantotnghiep/loaihang.php";
    private ProgressDialog pd;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_uong);
        edMaMn = findViewById(R.id.edtMaMn);
        edTenLh =findViewById(R.id.edtTenLh);
        edGiatien =findViewById(R.id.edtGiaTien);
        SpnTenLh = findViewById(R.id.SpnTenLh);

        btnThemmenu =findViewById(R.id.btnThemmenu);
        btnxemmenu = findViewById(R.id.btnxemmenu);
        backThemDU = findViewById(R.id.backThemDU);
        btnThemmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mamn = edMaMn.getText().toString();
                String tendu = edTenLh.getText().toString();
                String giatien = edGiatien.getText().toString();
                String tenlh = SpnTenLh.getSelectedItem().toString();
                String base64Image = encodeBitmapToBase64(bitmap);

                boolean fieldsEmpty = mamn.isEmpty() || tendu.isEmpty() || giatien.isEmpty();

                if (fieldsEmpty) {
                    StringBuilder errorMessage = new StringBuilder("Vui lòng nhập đầy đủ thông tin:\n");

                    if (mamn.isEmpty()) {
                        errorMessage.append("- Mã món\n");
                    }
                    if (tendu.isEmpty()) {
                        errorMessage.append("- Tên đồ uống\n");
                    }
                    if (giatien.isEmpty()) {
                        errorMessage.append("- Giá tiền\n");
                    }

                    Toast.makeText(ThemDoUong.this, errorMessage.toString(), Toast.LENGTH_LONG).show();
                } else {
                    if (!isNumeric(giatien)) {
                        Toast.makeText(ThemDoUong.this, "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    registerMenu(mamn, tendu, giatien, tenlh, base64Image);

                    // Clear input fields after successful registration
                    edMaMn.setText("");
                    edTenLh.setText("");
                    edGiatien.setText("");
                }
            }
        });
        pd = new ProgressDialog(ThemDoUong.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new ThemDoUong.MyAsyncTask().execute(urllink1);
        btnxemmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        backThemDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icthemanhmenu1 = findViewById(R.id.icthemanhmenu1);
        String imageUrl = BASE_URL + "duantotnghiep/layhinhanhmenu.php?MaMn=" + edMaMn;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        icthemanhmenu1.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ThemDoUong.this, "Thêm Avatar", Toast.LENGTH_SHORT).show();
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
                        icthemanhmenu1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }

                }
            }
        });
        icthemanhmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });

    }
    private String encodeBitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)) {
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } else {
            return null; // or throw an exception, depending on your requirements
        }
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
            Log.d("DEBUG01", "tenLhValues: " + lsuList.toString());
        }

        ArrayAdapter<String> spinnerMaLhAdapter = new ArrayAdapter<>(ThemDoUong.this, android.R.layout.simple_spinner_item, maLhValues);
        spinnerMaLhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpnTenLh.setAdapter(spinnerMaLhAdapter);
    }
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public void registerMenu(String MaMn , String TenDu, String Giatien, String TenLh, String Hinhanh1) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Menu menu = new Menu();
        menu.setMaMn(MaMn);
        menu.setTenDu(TenDu);
        menu.setGiatien(Integer.parseInt(String.valueOf(Giatien)));
        menu.setTenLh(TenLh);
        menu.setHinhanh1(Hinhanh1);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.MENU);
        serverRequest.setMenu(menu);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ThemDoUong.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThemDoUong.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }
        });
    }
    private void handleResponse(ServerResponse response) {
        if (response != null) {
            if (response.getResult().equals(Constants.SUCCESS)) {
                Toast.makeText(ThemDoUong.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                // If you need to update the Spinner data after a successful response, you can do it here.
                fetchDataForSpinner();
            } else {
                Toast.makeText(ThemDoUong.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(Constants.TAG, "Response body is null");
        }
    }
    private void fetchDataForSpinner() {
        // Perform the Retrofit request to fetch data for the Spinner
        // Similar to what you were doing in the AsyncTask
        // Populate the Spinner after fetching the data
    }
}