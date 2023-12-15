package gmail.com.qlcafepoly.admin;

import static java.security.AccessController.getContext;
import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
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

public class Suathongtinhh extends AppCompatActivity {
    Bitmap bitmap;
    private EditText edMahh,  edTenhh, edGiatien, edGhichu, edSoluong;
    private TextView textView5 ,edMancc, edMalh;
    private Button buttonSave;
    private ImageView imageView;
    ThongTinHangNhap thongTinHangNhap;

    Button btnquaylai, btnSua;
    TextView Mahh;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtinhh);

        Intent intent = getIntent();
        String maHH = intent.getStringExtra("DULIEU");
        String maNcc = intent.getStringExtra("DULIEU_MaNcc");
        String maLh = intent.getStringExtra("DULIEU_MaLh");
        String Tehh = intent.getStringExtra("DULIEU_TenHh");
        String Gia = intent.getStringExtra("DULIEU_GiaSp");
        String ghichu = intent.getStringExtra("DULIEU_Ghichu");
        String soluong = intent.getStringExtra("DULIEU_Soluong");


        TextView textViewTehh = findViewById(R.id.edTenhhs);
        TextView textViewgia = findViewById(R.id.edTennccs);
        TextView textViewGhichu = findViewById(R.id.edDiachis);
        TextView textViewsoluong = findViewById(R.id.edSDts);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isLetterOrDigit(character) && !Character.isSpaceChar(character) && character != ',') {
                        Toast.makeText(getApplicationContext(), "Không được kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };

        textViewTehh.setFilters(new InputFilter[]{filter});

        ImageView view = findViewById(R.id.anhkho);

        TextView capnhatanh = findViewById(R.id.textView19);
        capnhatanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaanhkho();
            }
        });


        textViewTehh.setText(Tehh);
        textViewgia.setText(Gia);
        textViewGhichu.setText(ghichu);
        textViewsoluong.setText(soluong);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        view.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }

                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });

        String imageUrl = BASE_URL + "duantotnghiep/layanhkho.php?MaHH=" + maHH;
        view.setTag(imageUrl);
        Picasso.get().invalidate(imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(view, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                    }
                });


        btnSua = findViewById(R.id.btnSuas);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tehh = textViewTehh.getText().toString();
                String giatien = textViewgia.getText().toString();
                String ghichu = textViewGhichu.getText().toString();
                String soluong = textViewsoluong.getText().toString();

                Suahanghoa(maHH, maNcc, maLh, tehh, giatien, ghichu, soluong);


            }
        });

        btnquaylai = findViewById(R.id.btnQuaylais);
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void Suahanghoa(String MaHH , String MaNcc, String TenLh,String TenHh,String GiaSp,String Ghichu, String Soluong ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User1 user1 = new User1();
        user1.setMaHH(MaHH);
        user1.setMaNcc(MaNcc);
        user1.setTenLh(TenLh);
        user1.setTenHh(TenHh);
        user1.setGiaSp(GiaSp);
        user1.setGhichu(Ghichu);
        user1.setSoluong(Soluong);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUAHANGHOA);
        serverRequest.setUser1(user1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" +t.getMessage());

            }
        });
    }
    public void suaanhkho(){
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base64img = Base64.encodeToString(bytes, Base64.DEFAULT);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            String url = BASE_URL + "duantotnghiep/capnhatanhkho.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(Suathongtinhh.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Suathongtinhh.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                Intent intent = getIntent();
                String maHH = intent.getStringExtra("DULIEU");
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("image", base64img);
                    paramV.put("MaHH", maHH); // Bổ sung thông tin tên khách hàng
                    return paramV;
                }
            };
            queue.add(stringRequest);


        } else {
            Toast.makeText(Suathongtinhh.this, "Chọn ảnh thay đổi", Toast.LENGTH_SHORT).show();
        }
    }






}