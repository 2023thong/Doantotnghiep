package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.model.Menu;

public class MenuDU extends BaseAdapter {
    private List<Menu> menu1;
    private LayoutInflater inflater1;

    public MenuDU(Context context, List<Menu> menu1){
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



        tvTenDu.setText(menu.getTenLh());
        tvGiatien.setText(String.valueOf(menu.getGiatien()));


        return convertView;
    }
}
