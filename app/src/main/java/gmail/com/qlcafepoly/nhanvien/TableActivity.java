package gmail.com.qlcafepoly.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.ThemDoUong;

import gmail.com.qlcafepoly.model.Ban;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableActivity extends AppCompatActivity {
    private List<Ban> banList = new ArrayList<>();
    private Table1 adapter;

    FloatingActionButton themban;
    ImageView backban;
    private ListView lsban;

    private String urllink = "http://192.168.1.110:8080/duantotnghiep/thongtinban.php";


    private ProgressDialog pd;
    private Spinner spnTrangthai;

    private EditText edtMaban, edtTenBan ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        themban = findViewById(R.id.themban);
        themban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBanDialog();
            }
        });
        backban = findViewById(R.id.backban);
        backban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        lsban = findViewById(R.id.lvtable);

        adapter = new Table1(TableActivity.this,banList);
        lsban.setAdapter(adapter);

        pd = new ProgressDialog(TableActivity.this); // Khởi tạo ProgressDialog ở đây
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);



        new MyAsyncTask().execute(urllink);
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

                    banList.clear();;

                    JSONArray jsonArrayban = jsonObject.getJSONArray("ban");
                    Log.d("//=====size===", jsonArrayban.length() + "");

                    for (int i = 0; i < jsonArrayban.length(); i++) {
                        JSONObject banObject = jsonArrayban.getJSONObject(i);
                        Log.d("MaBn", banObject.getString("MaBn"));
                        Log.d("TenBan", banObject.getString("TenBan"));
                        Log.d("Trangthai", banObject.getString("Trangthai"));

                        String MaBn = banObject.getString("MaBn");
                        String TenBan = banObject.getString("TenBan");
                        String Trangthai = banObject.getString("Trangthai");



                        Ban ban = new Ban();
                        ban.setMaBn(MaBn);
                        ban.setTenBan(TenBan);
                        ban.setTrangthai(Trangthai);
                        banList.add(ban);

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
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
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

    private void updateData(){
        new MyAsyncTask().execute(urllink);
    }



    private void showAddBanDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_themban, null);
        dialogBuilder.setView(dialogView);

        edtTenBan = dialogView.findViewById(R.id.edtTenban);
        edtMaban = dialogView.findViewById(R.id.edtMaban);
        spnTrangthai = dialogView.findViewById(R.id.spnTrangthai);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.trangthai_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTrangthai.setAdapter(adapter);

        dialogBuilder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Handle the data entered by the user and add it to the server
                String TenBan = edtTenBan.getText().toString();
                String MaBn = edtMaban.getText().toString();
                String Trangthai = getTrangThaiValue(spnTrangthai.getSelectedItemPosition());
                dialog.dismiss();


                registerBan(MaBn, TenBan, Trangthai);

                edtMaban.setText("");
                edtTenBan.setText("");
                spnTrangthai.setSelection(0);



                updateData();
            }
        });

        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    private String getTrangThaiValue(int selectedItemPosition) {
        if (selectedItemPosition == 0) {
            return "1"; // Map 'trống' to '1'
        } else {
            return "2"; // Map 'đầy' to '2'
        }
    }
    public void registerBan(String MaBn , String TenBan, String Trangthai ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Ban ban = new Ban();
        ban.setMaBn(MaBn);
        ban.setTenBan(TenBan);
        ban.setTrangthai(Trangthai);
        RequestInterface.ServerRequest serverRequest = new RequestInterface.ServerRequest();
        serverRequest.setOperation(Constants.BAN);
        serverRequest.setBan(ban);
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse response1 = response.body();
                if (response1.getResult().equals(Constants.SUCCESS)){
                    Toast.makeText(TableActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(TableActivity.this, response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "Failed"+ t.getMessage());

            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
    }
}