package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import gmail.com.qlcafepoly.R;

// PayDU.java
public class PayDU extends BaseAdapter {
    private List<Menu1> menu2;
    private LayoutInflater inflater;
    private Context context;

    public PayDU(Context context, List<Menu1> menu2) {
        this.context = context;
        this.menu2 = menu2;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return menu2.size();
    }

    @Override
    public Object getItem(int position) {
        return menu2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menupay, parent, false);
        }

        Menu1 menu1 = menu2.get(position);

        TextView tv_DoUong = convertView.findViewById(R.id.tv_DoUong);
        TextView tvMoney = convertView.findViewById(R.id.tvMoney);
        TextView tv_Soluong = convertView.findViewById(R.id.tv_Soluong);
        ImageView view = convertView.findViewById(R.id.img_Douong1);

        tv_DoUong.setText(menu1.getTenDu());
        tvMoney.setText(formatCurrency(Double.parseDouble(String.valueOf(menu1.getGiatien()))));
        tv_Soluong.setText(String.valueOf(menu1.getSoluong()));

        String imageUrl = BASE_URL + "duantotnghiep/layanhthongtintt.php?TenDu=" + menu1.getTenDu();
        view.setTag(imageUrl);
        Picasso.get().invalidate(imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(view, new Callback() {
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
    public void sortListByMaOderAndTenDu() {
        Collections.sort(menu2, new Comparator<Menu1>() {
            @Override
            public int compare(Menu1 menu1, Menu1 menu2) {
                // So sánh theo MaOder
                int compareMaOder = Integer.compare(menu1.getMaOder(), menu2.getMaOder());

                // Nếu MaOder khác nhau, trả về kết quả so sánh MaOder
                if (compareMaOder != 0) {
                    return compareMaOder;
                }

                // Nếu MaOder giống nhau, so sánh theo TenDu
                return menu1.getTenDu().compareTo(menu2.getTenDu());
            }
        });
        notifyDataSetChanged();
    }
    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(value);
    }


}