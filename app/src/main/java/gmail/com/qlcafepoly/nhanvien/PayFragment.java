package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Pay1;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class PayFragment extends Fragment {
    private List<Thongtinoder> listPay = new ArrayList<>();
    private Pay1 pay1;

    private ImageView imageView;
    private Button btnxemdanhsachban;
    private ListView lv_listpay;

    private TextView ngay, loc;
    private String urllink = BASE_URL + "duantotnghiep/oder.php";

    private ProgressDialog pd;

    private Calendar calendar;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pay, container, false);
        imageView = view.findViewById(R.id.img_Douong);
        lv_listpay = view.findViewById(R.id.lv_listpay);
        btnxemdanhsachban = view.findViewById(R.id.btnxemdanhsachban);
        ngay = view.findViewById(R.id.ngaychọn);


        calendar = Calendar.getInstance();
        updateEditText();
        filterListByDate();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new MyAsyncTask().execute(urllink);
        ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        return view;
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
                    JSONArray jsonArrayPay = jsonObject.getJSONArray("oder");
                    Log.d("//=====size=====", jsonArrayPay.length() + "");
                    for (int i = 0; i < jsonArrayPay.length(); i++) {
                        JSONObject payObject = jsonArrayPay.getJSONObject(i);
                        parseAndAddToList(payObject);
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
            try {
                if (getActivity() != null) {
                    filterListByDate();
                }

            }catch (Exception e){
                e.getStackTrace();
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
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                return stringBuilder.toString();
            } catch (Exception ex) {
                Log.d("Error: ", ex.toString());
            }
            return null;
        }

        private void parseAndAddToList(JSONObject payObject) throws JSONException {
            Log.d("MaOder", payObject.getString("MaOder"));
            Log.d("MaBn", payObject.getString("MaBn"));
            Log.d("TongTien", payObject.getString("TongTien"));
            Log.d("Ngay", payObject.getString("Ngay"));

            String maOder = payObject.getString("MaOder");
            String maBn = payObject.getString("MaBn");
            int trangThai = payObject.getInt("TrangThai");
            String tongTien = payObject.getString("TongTien");
            String traTien = payObject.getString("TrangThai");
            String ngay = payObject.getString("Ngay");

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.###");
            String formattedTongTien = decimalFormat.format(Double.parseDouble(tongTien));

            Thongtinoder thongtinoder = new Thongtinoder();
            thongtinoder.setMaOder(Integer.parseInt(maOder));
            thongtinoder.setMaBn(new String(maBn));
            thongtinoder.setTrangThai(trangThai);
            thongtinoder.setTongTien(Integer.parseInt(tongTien));
            thongtinoder.setFormattedTongtien(formattedTongTien);
            thongtinoder.setTratien(Integer.parseInt(traTien));
            thongtinoder.setNgay(formatDate(ngay));

            listPay.add(thongtinoder);
        }
    }

    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                view.getContext(),
                (DatePicker view1, int year1, int month1, int dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateEditText();
                    Loc();
                    filterListByDate();
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void updateEditText() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ngay.setText(sdf.format(calendar.getTime()));
    }

    private void filterListByDate() {
        String selectedDate = ngay.getText().toString();

        List<Thongtinoder> filteredList = new ArrayList<>();
        for (Thongtinoder thongtinoder : listPay) {
            if (thongtinoder.getTrangThai() == 1 && thongtinoder.getNgay().equals(selectedDate)) {
                filteredList.add(thongtinoder);
            }
        }
        pay1 = new Pay1(getActivity(), filteredList);
        lv_listpay.setAdapter(pay1);
        try {

            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date date = sdf.parse(selectedDate);
            String formattedDate = sdf.format(date);
            ngay.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void Loc() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Ngay", sdf.format(calendar.getTime()));
        editor.apply();
        Log.d("testthong", sdf.format(calendar.getTime()));
    }

    private String formatDate(String date) {
        if (date == null) {
            return ""; // Or handle the null case based on your requirements
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date parsedDate = inputFormat.parse(date);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date; // Return the original date if an exception occurs
    }
}
