package gmail.com.qlcafepoly.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Menuht extends BaseAdapter {
    private List<Menu> menudu;
    private LayoutInflater inflater;
    public Menuht(Context context, List<Menu> menudu) {
        this.menudu = menudu;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menudu.size();
    }

    @Override
    public Object getItem(int position) {
        return menudu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_do_uong, parent, false);
        }
        Menu menu = menudu.get(position);

        TextView tvMaMn = convertView.findViewById(R.id.tvMaMn);
        TextView tvTenLh = convertView.findViewById(R.id.tvTenLh);
        TextView tvGiatien = convertView.findViewById(R.id.tvGiatien);


        tvMaMn.setText(menu.getMaMn());
        tvTenLh.setText(menu.getTenLh());
        tvGiatien.setText(String.valueOf(menu.getGiatien()));


        return convertView;
    }
    }
