package gmail.com.qlcafepoly.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    private EditText edMaNv, edTenNv, edTenDn, edMatkhau, edSdt, edDiachi ,edChucvu;
    private Button btnThemNV, btnXemNV;
    private ArrayList<User> nhanVienList = new ArrayList<>();
    private ArrayAdapter customAdapter;

    @SuppressLint("WrongViewCast")
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

                if (manv.isEmpty() || tennv.isEmpty() || tendn.isEmpty() || matkhau.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || chucvu.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder("Vui lòng nhập đầy đủ thông tin:\n");

                    if (manv.isEmpty()) {
                        errorMessage.append("- Mã nhân viên\n");
                    }
                    if (tennv.isEmpty()) {
                        errorMessage.append("- Tên nhân viên\n");
                    }
                    if (tendn.isEmpty()) {
                        errorMessage.append("- Tên đăng nhập\n");
                    }
                    if (matkhau.isEmpty()) {
                        errorMessage.append("- Mật khẩu\n");
                    }
                    if (sdt.isEmpty()) {
                        errorMessage.append("- Số điện thoại\n");
                    }
                    if (diachi.isEmpty()) {
                        errorMessage.append("- Địa chỉ\n");
                    }
                    if (chucvu.isEmpty()) {
                        errorMessage.append("- Chức vụ\n");
                    }

                    Toast.makeText(ThemNhanVien.this, errorMessage.toString(), Toast.LENGTH_LONG).show();
                } else {
                    // Validate Chucvu
                    int chucVuValue;
                    try {
                        chucVuValue = Integer.parseInt(chucvu);
                        if (chucVuValue != 1 && chucVuValue != 2) {
                            Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số '1' hoặc '2'", Toast.LENGTH_SHORT).show();
                            return; // Do not proceed with the registration if Chucvu is invalid.
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ThemNhanVien.this, "Chức vụ phải là số '1' hoặc '2'", Toast.LENGTH_SHORT).show();
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

                    registerProcess2(manv, tennv, tendn, matkhau, sdt, diachi, chucvu);

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
    }
    private boolean isNameValid(String name) {
        return name.matches("^[\\p{L} .'-]+$");
    }

    // Function to check if the phone number contains only digits
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
    public void registerProcess2(String MaNv, String TenNv, String TenDn, String Matkhau, String Sdt, String Diachi, String Chucvu) {
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

