package gmail.com.qlcafepoly.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import gmail.com.qlcafepoly.R;

public class Thonhtintaikhoan extends AppCompatActivity {
    ImageView back1;

    Bitmap bitmap;

    ImageView imgchon;
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thonhtintaikhoan);

        TextView tvManv = findViewById(R.id.tvManv1);
        TextView tvTennv = findViewById(R.id.tvTennv);
        TextView tvSdt = findViewById(R.id.tvSDt);
        TextView tvDiachi = findViewById(R.id.diachi);
        TextView tvChucvu = findViewById(R.id.tvPhanquyen);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("thong", Context.MODE_PRIVATE);
        String Manv = sharedPreferences.getString("Manv", "");
        String Tennv = sharedPreferences.getString("TenNv", "");
        String Sdt = sharedPreferences.getString("Sdt", "");
        String Diachi = sharedPreferences.getString("Diachi", "");

        tvManv.setText(Manv);
        tvTennv.setText(Tennv);
        tvSdt.setText(Sdt);
        tvDiachi.setText(Diachi);
        tvChucvu.setText("Amdin");

        imgchon = findViewById(R.id.imgAnhdd);
        button = findViewById(R.id.btnthem);




        //hienanh



        // Gửi tên tài khoản lên máy chủ để lấy hình ảnh
        String imageUrl = "http://192.168.1.100:8080/duantotnghiep/layhinhanh.php?MaNv=" +Manv;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imgchon.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Thonhtintaikhoan.this, "Thêm Avatar", Toast.LENGTH_SHORT).show();
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
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://192.168.1.100:8080/duantotnghiep/database.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(Thonhtintaikhoan.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Thonhtintaikhoan.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
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
                    Toast.makeText(Thonhtintaikhoan.this, "Chọn ảnh thay đổi", Toast.LENGTH_SHORT).show();
                }
            }
        });



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

}