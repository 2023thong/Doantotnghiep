package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import gmail.com.qlcafepoly.R;

import gmail.com.qlcafepoly.admin.Thonhtintaikhoan;
import gmail.com.qlcafepoly.dangnhap.Danhnhap;
import gmail.com.qlcafepoly.dangnhap.DoiMatKhau;
import gmail.com.qlcafepoly.dangnhap.DoiMatKhauNv;


public class NhanVienFragment extends Fragment {
    Bitmap bitmap;

    ImageView imgchon;
    Button button;



    TextView tvManv,tvTennv,tvMatKhau,tvChucVu,tvSdt,tvDiaChi,tv_logout,tvDoiMK,tvCaLam;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhan_vien2, container, false);
        tvMatKhau = view.findViewById(R.id.tv_MatKhau);
        tvChucVu = view.findViewById(R.id.tv_ChucVu);
        tvSdt= view.findViewById(R.id.tv_Sdt);
        tvManv= view.findViewById(R.id.tv_MaNv);
        tvTennv = view.findViewById(R.id.tv_tenNv);
        tvDiaChi = view.findViewById(R.id.tv_DiaChi);
        tv_logout = view.findViewById(R.id.tvlogout);
        tvDoiMK = view.findViewById(R.id.tvDoiMK);
        imgchon = view.findViewById(R.id.imgThemAnh);
        button = view.findViewById(R.id.btnLuu);

//        tvCaLam = view.findViewById(R.id.tv_Calam);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("van", Context.MODE_PRIVATE);
        String Manv = sharedPreferences.getString("Manv", "");
        String TenNv = sharedPreferences.getString("TenNv","");
        String MatKhau = sharedPreferences.getString("Matkhau","");

        String SDT = sharedPreferences.getString("Sdt","");
        String DiaChi = sharedPreferences.getString("Diachi","");
        String MaCL = sharedPreferences.getString("MaCl", "");
        Log.e("====///",""+DiaChi);
        Log.e("====///",""+MaCL);
        tvTennv.setText(TenNv);
//        tvCaLam.setText(MaCL);
        tvMatKhau.setText(MatKhau);
        tvSdt.setText(SDT);
        tvDiaChi.setText(DiaChi);
        tvManv.setText(Manv);
        tvChucVu.setText("Nhân Viên");
        Sukien();
        String imageUrl = BASE_URL +"duantotnghiep/layhinhanh.php?MaNv=" +Manv;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView imageView = view.findViewById(R.id.imgThemAnh);

                        imageView.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "Thêm Avatar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(imageRequest);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                                imgchon.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.getStackTrace();
                            }

                        }
                    }
                });
        imgchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaNv = tvManv.getText().toString();
                Log.d("/tennv" , MaNv);
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64img = Base64.encodeToString(bytes, Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(requireContext().getApplicationContext());
                    String url = BASE_URL +"duantotnghiep/database.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(requireContext(), "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(requireContext().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(requireContext(), "Chọn ảnh thay đổi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imgchon.setImageBitmap(result);
            }
        }
    }
    private void Sukien() {
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireContext(), Danhnhap.class));
            }
        });
        tvDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireContext(), DoiMatKhauNv.class));
            }
        });
    }
}