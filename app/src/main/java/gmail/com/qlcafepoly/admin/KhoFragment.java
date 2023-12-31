package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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

public class KhoFragment extends Fragment {
    Bitmap bitmap;
    private EditText edMahh, edMancc, edMalh, edTenhh, edGiatien, edGhichu, edSoluong;
    private TextView   Capnhatkho;
    private Button buttonSave;
    private ImageView textView5 , imageView2, imgchon;

    private List<User1> lsuList = new ArrayList<>();
    private List<User1> maLhList = new ArrayList<>();
    private Hanghoaht adepter;

    private Spinner spinnerMaNcc, spinner;
    private ProgressDialog pd;

    private String urllink = BASE_URL +"duantotnghiep/thu.php";
    private String urllink1 =BASE_URL + "duantotnghiep/loaihang.php";

    private static final int PICK_IMAGE_REQUEST = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho, container, false);
        edMahh = view.findViewById(R.id.tvMahh1);

        edTenhh = view.findViewById(R.id.edTenhh);
        edGiatien = view.findViewById(R.id.edGiatien);
        edGhichu = view.findViewById(R.id.edGhichu);
        edSoluong = view.findViewById(R.id.edSoluong);
        imgchon = view.findViewById(R.id.chon);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isLetterOrDigit(character) && !Character.isSpaceChar(character)) {
                        Toast.makeText(getContext(), "Không được kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };

        edTenhh.setFilters(new InputFilter[]{filter});


        textView5 = view.findViewById(R.id.imgkho);
        ImageView imageView1 = view.findViewById(R.id.imageView2);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThemNhaCungCap.class);
                startActivity(intent);
            }
        });
        Capnhatkho = view.findViewById(R.id.capnhatkho);
        Capnhatkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new KhoFragment());
            }
        });
        imageView2 = view.findViewById(R.id.imageView3);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layoutthemlh, null);

                final TextInputLayout loaihangLayout = dialogView.findViewById(R.id.loaihang_layout);
                final TextInputLayout ghichuLayout = dialogView.findViewById(R.id.ghichu_layout);
                final EditText loaihang = dialogView.findViewById(R.id.loaihang);
                final EditText ghichu = dialogView.findViewById(R.id.ghichu);
                builder.setView(dialogView);

                builder.setTitle("Thêm Loại Hàng")
                        .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String inputLoaihang = loaihang.getText().toString();
                                String inputGhichu = ghichu.getText().toString();
                                ThemLoaiHang(inputLoaihang, inputGhichu);
                                dialogInterface.dismiss();

                            }
                        })
                        .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThongTinHangNhap.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        imgchon.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
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


        buttonSave = view.findViewById(R.id.btnLuukho);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String mancc = spinnerMaNcc.getSelectedItem().toString();
                    String malh = spinner.getSelectedItem().toString();
                    String tehh = edTenhh.getText().toString();
                    String giatien = edGiatien.getText().toString();
                    String ghichu = edGhichu.getText().toString();
                    String soluong = edSoluong.getText().toString();
                    String base64Image = encodeBitmapToBase64(bitmap);

                    registerProcess1( mancc, malh, tehh, giatien, ghichu, soluong, base64Image);


                edTenhh.setText("");
                edGiatien.setText("");
                edGhichu.setText("");
                edSoluong.setText("");
//                ImageView imgchon = container.findViewById(R.id.chon);
//                imgchon.setImageResource(R.drawable.cam);


            }
        });
        spinnerMaNcc = view.findViewById(R.id.spinnerMaNcc);
        spinner = view.findViewById(R.id.spinner);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new KhoFragment.MyAsyncTask().execute(urllink);
        new KhoFragment.MyAsyncTask1().execute(urllink1);
        return view;
    }

    public void registerProcess1( String MaNcc, String TenLh, String TenHh, String GiaSp, String Ghichu, String Soluong, String imageBase64) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);



        User1 user1 = new User1();
        user1.setMaNcc(MaNcc); // Use the selected MaNcc value
        user1.setTenLh(TenLh);
        user1.setTenHh(TenHh);
        user1.setGiaSp(GiaSp);
        user1.setGhichu(Ghichu);
        user1.setSoluong(Soluong);
        user1.setImageBase64(imageBase64); // Add the base64 encoded image data

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.HANGHOA);
        serverRequest.setUser1(user1);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }
        });
    }
    private String encodeImageToBase64(String imagePath) {
        // Read the image file and encode it to base64
        try {
            InputStream inputStream = new FileInputStream(imagePath);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Đang tải dữ liệu...");
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
                    if (jsonObject.has("nhacungcap")) {
                        JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("nhacungcap");
                        Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                        for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                            JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                            Log.d("MaNcc", nhanvienObject.getString("MaNcc"));

                            String MaNcc = nhanvienObject.getString("MaNcc");
                            User1 user1 = new User1();
                            user1.setMaNcc(MaNcc);
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
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (getActivity() != null) {
                populateSpinnerWithMaNcc();
                populateSpinnerWithMaLh();
            }
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
    private void populateSpinnerWithMaNcc() {
        List<String> maNccValues = new ArrayList<>();
        for (User1 user : lsuList) {
            String maNcc = user.getMaNcc();
            maNccValues.add(maNcc);

        }

        ArrayAdapter<String> spinnerMaNccAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, maNccValues);
        spinnerMaNccAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaNcc.setAdapter(spinnerMaNccAdapter);
    }

    private class MyAsyncTask1 extends AsyncTask<String, Void, String> {
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

                            String MaLh = nhanvienObject.getString("TenLh");
                            User1 user1 = new User1();
                            user1.setTenLh(MaLh);
                            maLhList.add(user1);
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
                throw new RuntimeException(e);
            }
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (getActivity() != null) {
                populateSpinnerWithMaLh();
            }
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
        for (User1 user : maLhList) {
            String maLh = user.getTenLh();
            maLhValues.add(maLh);
            Log.d("DEBUG01", "tenLhValues: " + maLhList.toString());
        }
        ArrayAdapter<String> spinnerMaLhAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, maLhValues);
        spinnerMaLhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerMaLhAdapter);
    }
    @SuppressLint("NotConstructor")
    public void ThemLoaiHang(String TenLh, String Ghichu) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        LoaiHang user2 = new  LoaiHang();
        user2.setTenLh(TenLh);
        user2.setGhichu(Ghichu);

        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.THEMLOAIHANG);
        serverRequest.setLoaihang(user2);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);

        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed" + t.getMessage());
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đóng kết nối đến CSDL khi Fragment bị hủy

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framg, fragment);
        fragmentTransaction.commit();
    }
    private String encodeBitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Check if the compression is successful
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)) {
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } else {
            return null; // or throw an exception, depending on your requirements
        }
    }



}
