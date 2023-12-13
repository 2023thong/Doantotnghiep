package gmail.com.qlcafepoly.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Hoadon;

public class DoanhThu extends BaseAdapter {
    private final Context context;
    private List<Hoadon> users;


    public DoanhThu(Context context, List<Hoadon> users) {
        this.context = context;
        this.users = users;
        // Sắp xếp danh sách theo thời gian giảm dần (ngày gần nhất lên đầu)

    }

    @Override
    public int getCount() {
        return
        users.size();

    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doanhthu, viewGroup, false);
        }

        Hoadon hoadon = users.get(position);
        TextView thu1 = convertView.findViewById(R.id.ngay0);
        TextView thu2 = convertView.findViewById(R.id.tiendt0);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedTongTien = numberFormat.format(hoadon.getTongTien());
        thu1.setText(hoadon.getThoigian());
        thu2.setText("Thu: "+formattedTongTien +" VND");
        return convertView;
    }
}
