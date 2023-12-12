package gmail.com.qlcafepoly.nhanvien;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        tv_DoUong.setText(menu1.getTenDu());
        String formattedMoney = formatCurrency(menu1.getGiatien());
        tvMoney.setText(formattedMoney);
        tv_Soluong.setText(String.valueOf(menu1.getSoluong()));


        return convertView;
    }
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(amount);
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


}