package gmail.com.qlcafepoly.nhanvien;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Hanghoaht;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.Suathongtinhh;

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

//        tvMenu.setText(menu.getMaMn());
        tvTenLh.setText(menu.getTenDu());
        tvGiatien.setText(String.valueOf(menu.getGiatien()));





        return convertView;
    }

}
