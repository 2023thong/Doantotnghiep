package gmail.com.qlcafepoly.Menu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.hoadonApdater;
import gmail.com.qlcafepoly.model.Menu;
import gmail.com.qlcafepoly.model.hoadonModel;

public class DuUong extends BaseAdapter {
    private List<Menu> menus;
    private LayoutInflater inflater;
    private Button btn_add,btn_remover;
    private TextView tvSoLuong;
    private hoadonModel hoadonModel;
    private DuUong duUong;
    public DuUong(Context context, List<Menu> menus) {
        this.menus = menus;

        inflater = LayoutInflater.from(context);
    }
    public void setHoadonModel(hoadonModel hoadonModel) {
        this.hoadonModel = hoadonModel;
    }
    public void updateTongTien() {
        // Tính tổng giá tiền từ danh sách menu
        int tongTien = 0;
        for (Menu menu : menus) {
            tongTien += menu.getGiatien() * menu.getSoLuong();
        }

        // Cập nhật giá trị TongTien trong hoadonModel
        if (hoadonModel != null) {
            hoadonModel.setTongTien(tongTien);
        }

        // Cập nhật giao diện người dùng
        notifyDataSetChanged(); // Phương thức này giúp cập nhật giao diện ListView
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_menu_sql, parent, false);
        }

        Menu menu = menus.get(position);

        TextView tvNameDoUong = convertView.findViewById(R.id.tv_nameDoUong);
        TextView tvGia = convertView.findViewById(R.id.tv_gia);
        TextView tvSoLuong = convertView.findViewById(R.id.tv_soLuong);
        Button btnAdd = convertView.findViewById(R.id.btn_add);
        Button btnRemover = convertView.findViewById(R.id.btn_remover);

        tvNameDoUong.setText(menu.getTenLh());
        String giaFormatted = String.valueOf(menu.getGiatien()) + " Vnd";
        String soLuongFormatted = String.valueOf(menu.getSoLuong());
        tvGia.setText(giaFormatted);
        tvSoLuong.setText(soLuongFormatted);

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int giaTriHienTai = menu.getSoLuong();
                if (giaTriHienTai > 0) {
                    giaTriHienTai--;
                    menu.setSoLuong(giaTriHienTai);
                    tvSoLuong.setText(String.valueOf(giaTriHienTai));
                    duUong.updateTongTien();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int giaTriHienTai = menu.getSoLuong();
                giaTriHienTai++;
                menu.setSoLuong(giaTriHienTai);
                tvSoLuong.setText(String.valueOf(giaTriHienTai));
                duUong.updateTongTien();
            }
        });

        return convertView;
    }


}
