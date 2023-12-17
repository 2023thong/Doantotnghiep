package gmail.com.qlcafepoly.admin;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContentProviderCompat.requireContext;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

public class MaQr extends AppCompatActivity {
    Bitmap bitmap;
    ImageView view;
    private ProgressDialog pd;
    private List<Qr> lsuList = new ArrayList<>();

    private String urllink = BASE_URL +"duantotnghiep/getanhqr.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_qr);

        view = findViewById(R.id.imgqr);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(MaQr.this.getContentResolver(), uri);
                        view.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
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
        Button button = findViewById(R.id.buttonchon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sua();
            }
        });
        String imageUrl = BASE_URL + "duantotnghiep/getanhqr.php?Id=" + 2;
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

    }

    public void sua() {
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String base64img = Base64.encodeToString(bytes, Base64.DEFAULT);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            String url = BASE_URL + "duantotnghiep/suaqr.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(MaQr.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MaQr.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
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
                    paramV.put("Id", String.valueOf(2));
                    return paramV;
                }
            };
            queue.add(stringRequest);

        } else {
            Toast.makeText(MaQr.this, "Chọn qr thay đổi", Toast.LENGTH_SHORT).show();
        }
    }


}