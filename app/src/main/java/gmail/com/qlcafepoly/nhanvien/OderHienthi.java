package gmail.com.qlcafepoly.nhanvien;


import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Menu;

public class OderHienthi extends BaseAdapter {
    private List<gmail.com.qlcafepoly.admin.Menu> odermenu;
    private LayoutInflater inflater;



    public OderHienthi(Context context, List<gmail.com.qlcafepoly.admin.Menu> menuoder) {
        this.odermenu = menuoder;
        inflater = LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return odermenu.size();
    }

    @Override
    public Object getItem(int position) {
        return odermenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemhienmn, parent, false);
        }
        Menu menu = odermenu.get(position);

        TextView tvMenu = convertView.findViewById(R.id.tvMamn);

        TextView tvTenLh = convertView.findViewById(R.id.tvTenDuoder);
        TextView tvGiatien = convertView.findViewById(R.id.tvGiaDuOder);
        ImageView anh = convertView.findViewById(R.id.imganhkho1);


        tvTenLh.setText(menu.getTenDu());
        tvGiatien.setText(formatCurrency(Double.parseDouble(String.valueOf(menu.getGiatien()))));

        String imageUrl = BASE_URL + "duantotnghiep/layhinhanhmenu.php?MaMn=" + menu.getMaMn();
        anh.setTag(imageUrl);
        Picasso.get().invalidate(imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(anh, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                    }
                });

        return convertView;
    }
    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(value);
    }

}
