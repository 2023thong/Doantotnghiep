package gmail.com.qlcafepoly.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

import gmail.com.qlcafepoly.model.Menu;

public class DuUong extends BaseAdapter {
    private List<Menu> menus;
    private LayoutInflater inflater;

    public DuUong(Context context, List<Menu> menus) {
        this.menus = menus;
        inflater = LayoutInflater.from(context);
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

        Menu menus1 = menus.get(position);

        TextView tvNameDoUong = convertView.findViewById(R.id.tv_nameDoUong);
        TextView tvGia = convertView.findViewById(R.id.tv_gia);

        tvNameDoUong.setText(menus1.getTenLh());
        tvGia.setText(String.valueOf(menus1.getGiaTien()));



        return convertView;
    }
}
