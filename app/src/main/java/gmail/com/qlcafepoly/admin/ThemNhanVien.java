package gmail.com.qlcafepoly.admin;

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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemNhanVien extends AppCompatActivity {
    Bitmap bitmap;
    private ImageView icthemanhnhanvien1;
    private EditText edMaNv, edTenNv, edTenDn, edMatkhau, edSdt, edDiachi ,edChucvu;
    private Button btnThemNV, btnXemNV;
    private ArrayList<User> nhanVienList = new ArrayList<>();
    private ArrayAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        edMaNv = findViewById(R.id.edtMaNv);
        edTenNv =findViewById(R.id.edtTenNv);
        edTenDn =findViewById(R.id.edtTenDnnv);
        edMatkhau =findViewById(R.id.edtMatkhau);
        edSdt =findViewById(R.id.edtSdt);
        edDiachi =findViewById(R.id.edtDiachi);
        edChucvu =findViewById(R.id.edtChucvu);
        btnThemNV =findViewById(R.id.btnThemNV);
        btnXemNV = findViewById(R.id.btnXemNV);

        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = edMaNv.getText().toString();
                String tennv = edTenNv.getText().toString();
                String tendn = edTenDn.getText().toString();
                String matkhau = edMatkhau.getText().toString();
                String sdt = edSdt.getText().toString();
                String diachi = edDiachi.getText().toString();
                String chucvu = edChucvu.getText().toString();
                String base64Image = encodeBitmapToBase64(bitmap);

                if (manv.isEmpty() || tennv.isEmpty() || tendn.isEmpty() || matkhau.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || chucvu.isEmpty()) {
                    Toast.makeText(ThemNhanVien.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate Chucvu
                    int chucVuValue;
                    try {
                        chucVuValue = Integer.parseInt(chucvu);
                        if (chucVuValue != 1 && chucVuValue != 2) {
                            Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
                            return; // Do not proceed with the registration if Chucvu is invalid.
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
                        return; // Do not proceed with the registration if Chucvu is not a number.
                    }

                    // Validate and set input filters
                    if (!isNameValid(tennv)) {
                        Toast.makeText(ThemNhanVien.this, "Tên nhân viên chỉ được chứa kí tự chữ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isPhoneNumberValid(sdt)) {
                        Toast.makeText(ThemNhanVien.this, "Số điện thoại chỉ được chứa kí tự số", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    registerProcess2(manv, tennv, tendn, matkhau, sdt, diachi, chucvu, base64Image);

                    // Only clear fields if the validation and registration are successful
                    edMaNv.setText("");
                    edTenNv.setText("");
                    edTenDn.setText("");
                    edMatkhau.setText("");
                    edSdt.setText("");
                    edDiachi.setText("");
                    edChucvu.setText("");
                }
            }
        });
        btnXemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        icthemanhnhanvien1 = findViewById(R.id.imgthemanhnhanvien1);
        String imageUrl = BASE_URL + "duantotnghiep/layhinhanh.php?MaNv=" + edMaNv;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        icthemanhnhanvien1.setImageBitmap(response);
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
                        icthemanhnhanvien1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }

                }
            }
        });
        icthemanhnhanvien1.setOnClickListener(new View.OnClickListener() {
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
    private boolean isNameValid(String name) {
        return name.matches("^[\\p{L} .'-]+$");
    }

    // Function to check if the phone number contains only digits
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
    public void registerProcess2(String MaNv, String TenNv, String TenDn, String Matkhau, String Sdt, String Diachi, String Chucvu,  String Hinhanh1) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        // Validate Chucvu
        int chucVuValue;
        try {
            chucVuValue = Integer.parseInt(Chucvu);
            if (chucVuValue != 1 && chucVuValue != 2) {
                Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
                edChucvu.setText("");  // Clear only Chucvu field
                return; // Do not proceed with the registration if Chucvu is invalid.
            }
        } catch (NumberFormatException e) {
            Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số 1 hoặc 2", Toast.LENGTH_SHORT).show();
            edChucvu.setText("");  // Clear only Chucvu field
            return; // Do not proceed with the registration if Chucvu is not a number.
        }

        User user = new User();
        user.setMaNv(MaNv);
        user.setTenNv(TenNv);
        user.setTenDn(TenDn);
        user.setMatkhau(Matkhau);
        user.setSdt(Sdt);
        user.setDiachi(Diachi);
        user.setChucvu(chucVuValue);
        user.setHinhanh1(Hinhanh1);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.NHANVIEN);
        serverRequest.setUser(user);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(ThemNhanVien.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThemNhanVien.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed");
            }
        });
    }

    public void backra(View view){
        finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối đến CSDL khi Fragment bị hủy

    }
}

