package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Hoadon;

public class QuanLyDoanhThuNam extends AppCompatActivity {
    private List<Hoadon> lsuList= new ArrayList<>();
    private DoanhThuNam doanhThuNam;
    private TextView tvTongDoanhThu;
    private ImageView icLich;


    private String urllink = BASE_URL +"duantotnghiep/doanhthutheonam.php";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu_nam);
        ListView doanhthunam = findViewById(R.id.lsdoanhthunam);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        int totalAmount = GlobalData.totalAmount;
        doanhThuNam = new DoanhThuNam(QuanLyDoanhThuNam.this, lsuList);
        doanhthunam.setAdapter(doanhThuNam);
        tvTongDoanhThu.setText(String.valueOf(totalAmount) + " vnd");
        Button btnDTNgay = findViewById(R.id.btnDTNgay2);
        btnDTNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuNam.this, QuanLyDoanhThu.class);
                startActivity(intent);
            }
        });
        Button btnDTThang = findViewById(R.id.btnDTThang2);
        btnDTThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuNam.this, QuanLyDoanhThuThang.class);
                startActivity(intent);
            }
        });
        Button btnDTNam = findViewById(R.id.btnDTNam2);
        btnDTNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuNam.this, QuanLyDoanhThuNam.class);
                startActivity(intent);
            }
        });
        icLich = findViewById(R.id.icLich);
        icLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarDialog();
            }
        });
        new QuanLyDoanhThuNam.MyAsyncTask().execute(urllink);

    }
    private void showCalendarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_calendar, null);
        builder.setView(dialogView);

        CalendarView calendarViewDialog = dialogView.findViewById(R.id.calendarViewDialog);
        calendarViewDialog.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Handle the selected date change
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(QuanLyDoanhThuNam.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle OK button click if needed
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle Cancel button click if needed
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pd != null) {
                pd.setMessage("Loading...");
            } else {
                // Xử lý nếu pd là null
            }

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                String strJson = readJsonOnline(strings[0]);
                Log.d("//====", strJson);

                JSONObject jsonObject = new JSONObject(strJson);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("doanhthunam");
                    Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                    for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                        JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                        Log.d("Nam", nhanvienObject.getString("Nam"));
                        Log.d("DoanhThuNam", nhanvienObject.getString("DoanhThuNam"));
                        String Nam = nhanvienObject.getString("Nam");
                        String DTNam = nhanvienObject.getString("DoanhThuNam");
                        Hoadon hoadon = new Hoadon();
                        hoadon.setThoigian(Nam);
                        hoadon.setTongTien(Integer.parseInt(DTNam));
                        lsuList.add(hoadon);
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
            if (doanhThuNam != null) {
                doanhThuNam.notifyDataSetChanged();
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
    public void backdoanhthu2(View view){
        finish();
    }
}