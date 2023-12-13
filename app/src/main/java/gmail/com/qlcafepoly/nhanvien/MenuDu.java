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
import gmail.com.qlcafepoly.model.Menu;

public class MenuDu extends BaseAdapter {
    private List<Menu> menu1;
    private LayoutInflater inflater1;

    public MenuDu(Context context, List<Menu> menu1){
        this.menu1 = menu1;
        inflater1 = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menu1.size();
    }
    public Object getItem(int position){
        return menu1.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater1.inflate(R.layout.activity_item_menu, parent, false);
        }
        Menu menu = menu1.get(position);

        TextView tvTenDu = convertView.findViewById(R.id.txtTenLh);
        TextView tvGiatien = convertView.findViewById(R.id.txtGia1);
        ImageView anh1 = convertView.findViewById(R.id.anh);

        tvTenDu.setText(menu.getTenLh());

        tvGiatien.setText(formatCurrency(Double.parseDouble(String.valueOf(menu.getGiatien()))));

        String imageUrl1 = BASE_URL + "duantotnghiep/layhinhanhmenu.php?MaMn=" + menu.getMaMn();
        anh1.setTag(imageUrl1);
        Picasso.get().invalidate(imageUrl1);
        Picasso.get()
                .load(imageUrl1)
                .into(anh1, new Callback() {
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
