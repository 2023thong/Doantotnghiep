package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.Menu;

public class Hienthisua extends BaseAdapter {
    private List<Menu> odermenu;
    private LayoutInflater inflater;

    public Hienthisua(Context context, List<Menu> menuoder, String selectedMaoder) {
        this.odermenu = filterMenuByMaoder(menuoder, selectedMaoder);
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemoder, parent, false);
            holder = new ViewHolder();
            holder.customTextView = convertView.findViewById(R.id.tvTenoder1);
            holder.customTextView1 = convertView.findViewById(R.id.tvGiaDuOder1);
            holder.sl = convertView.findViewById(R.id.tvsl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Menu menu = odermenu.get(position);

        holder.customTextView.setText(menu.getTenLh());
        holder.customTextView1.setText(String.valueOf(menu.getGiatien()));
        holder.sl.setText(String.valueOf(menu.getSoluong()));

        return convertView;
    }

    private List<Menu> filterMenuByMaoder(List<Menu> menuList, String selectedMaoder) {
        List<Menu> filteredList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (String.valueOf(menu.getMaOder()).equals(selectedMaoder)) {
                filteredList.add(menu);
            }
        }
        return filteredList;
    }

    static class ViewHolder {
        TextView customTextView;
        TextView customTextView1;
        TextView sl;
    }


}
