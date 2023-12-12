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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

public class ItemThongTinNV extends AppCompatActivity {
    private ImageView icanhnhanvien;
    private TextView btnanhnhanvien;
    Bitmap bitmap;
    Button btnBackNV,btnSuaNV,btnXoaNV;
    EditText edMaNV,edTenNV,edTenDN,edMatKhau,edSdt,edDiaChi,edChucVu;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_thong_tin_nv);
        edMaNV = findViewById(R.id.edMaNV);
        edTenNV =findViewById(R.id.edTenNV);
        edTenDN =findViewById(R.id.edTenDN);
        edMatKhau =findViewById(R.id.edMatKhau);
        edSdt =findViewById(R.id.edSdt);
        edDiaChi =findViewById(R.id.edDiaChi);
        edChucVu =findViewById(R.id.edChucVu);
        btnBackNV = findViewById(R.id.btnBackNV);
        btnSuaNV = findViewById(R.id.btnSuaNV);
        btnXoaNV = findViewById(R.id.btnXoaNV);

        btnSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = edMaNV.getText().toString();
                String tennv = edTenNV.getText().toString();
                String tendn = edTenDN.getText().toString();
                String matkhau = edMatKhau.getText().toString();
                String sdt = edSdt.getText().toString();
                String diachi = edDiaChi.getText().toString();
                String chucvu = edChucVu.getText().toString();

                // Validate Chucvu
                int chucVuValue;
                try {
                    chucVuValue = Integer.parseInt(chucvu);
                    if (chucVuValue != 1 && chucVuValue != 2) {
                        Toast.makeText(ItemThongTinNV.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
                        return; // Do not proceed with the update if Chucvu is invalid.
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(ItemThongTinNV.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
                    return; // Do not proceed with the update if Chucvu is not a number.
                }

                // Validate and set input filters
                if (!isNameValid(tennv)) {
                    Toast.makeText(ItemThongTinNV.this, "Tên nhân viên chỉ được chứa kí tự chữ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isPhoneNumberValid(sdt)) {
                    Toast.makeText(ItemThongTinNV.this, "Số điện thoại chỉ được chứa kí tự số", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerProcess2(manv, tennv, tendn, matkhau, sdt, diachi, chucvu);
            }
        });
        btnBackNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String MaNv = intent.getStringExtra("DULIEUNV");
        String TenNv = intent.getStringExtra("DULIEUNV_TenNv");
        String TenDn = intent.getStringExtra("DULIEUNV_TenDn");
        String Matkhau = intent.getStringExtra("DULIEUNV_Matkhau");
        String Sdt = intent.getStringExtra("DULIEUNV_Sdt");
        String Diachi = intent.getStringExtra("DULIEUNV_Diachi");
        String Chucvu = intent.getStringExtra("DULIEUNV_Chucvu");

        EditText edMaNv = findViewById(R.id.edMaNV);
        EditText edTenNv = findViewById(R.id.edTenNV);
        EditText edTenDn = findViewById(R.id.edTenDN);
        EditText edMatkhau = findViewById(R.id.edMatKhau);
        EditText edSdt = findViewById(R.id.edSdt);
        EditText edDiachi = findViewById(R.id.edDiaChi);
        EditText edChucvu = findViewById(R.id.edChucVu);

        edMaNv.setText(MaNv);
        edTenNv.setText(TenNv);
        edTenDn.setText(TenDn);
        edMatkhau.setText(Matkhau);
        edSdt.setText(Sdt);
        edDiachi.setText(Diachi);
        edChucvu.setText(Chucvu);
        btnXoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
        icanhnhanvien = findViewById(R.id.icanhnhanvien);
        btnanhnhanvien = findViewById(R.id.btnanhnhanvien);
        //hienanh
        // Gửi tên tài khoản lên máy chủ để lấy hình ảnh
        String imageUrl = BASE_URL + "duantotnghiep/layhinhanh.php?MaNv=" + MaNv;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        icanhnhanvien.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItemThongTinNV.this, "Thêm Avatar", Toast.LENGTH_SHORT).show();
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
                        icanhnhanvien.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }

                }
            }
        });
        icanhnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });
        btnanhnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaNv = edMaNv.getText().toString();
                Log.d("/manv" , MaNv);
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64img = Base64.encodeToString(bytes, Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url =BASE_URL +"duantotnghiep/database.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(ItemThongTinNV.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ItemThongTinNV.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
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
                            paramV.put("MaNv", MaNv); // Bổ sung thông tin tên khách hàng
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);


                }
                else{
                    Toast.makeText(ItemThongTinNV.this, "Chọn ảnh thay đổi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNameValid(String name) {
        return name.matches("^[\\p{L} .'-]+$");
    }

    // Function to check if the phone number contains only digits
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Người dùng đã xác nhận xóa, thực hiện hành động xóa ở đây
                String manv = edMaNV.getText().toString();
                deleteEmployee(manv);
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
    public void registerProcess2(String MaNv , String TenNv, String TenDn,String Matkhau,String Sdt,String Diachi, String Chucvu ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        User user = new User();
        user.setMaNv(MaNv);
        user.setTenNv(TenNv);
        user.setTenDn(TenDn);
        user.setMatkhau(Matkhau);
        user.setSdt(Sdt);
        user.setDiachi(Diachi);
        user.setChucvu(Integer.parseInt(Chucvu));
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.SUANHANVIEN);
        serverRequest.setUser(user);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");

            }
        });
    }
    public void deleteEmployee(String MaNv) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setMaNv(MaNv);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.XOANHANVIEN);
        serverRequest.setUser(user);

        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    // Sau khi xóa thành công, quay về màn hình Quanlynv hoặc thực hiện các xử lý khác tùy ý.
                    Intent intent = new Intent(ItemThongTinNV.this, Quanlynv.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemThongTinNV.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");
            }
        });
    }
}