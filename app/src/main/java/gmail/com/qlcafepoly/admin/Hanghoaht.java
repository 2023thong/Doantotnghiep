package gmail.com.qlcafepoly.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Hanghoaht extends BaseAdapter {
    private List<User1> users;
    private LayoutInflater inflater;

    public Hanghoaht(Context context, List<User1> users) {
        this.users = users;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.hienthikhohang, parent, false);
        }

        User1 user1 = users.get(position);

        TextView tvMahh = convertView.findViewById(R.id.tvMahh);
        TextView tvMancc = convertView.findViewById(R.id.tvMancc);
        TextView tvTenhh = convertView.findViewById(R.id.tvTensp);
        TextView tvGiatien = convertView.findViewById(R.id.tvGiatien);


        tvMahh.setText(user1.getMaHH());
        tvMancc.setText(user1.getMaNcc());
        tvTenhh.setText(user1.getTenHh());
        tvGiatien.setText(user1.getGiaSp());


        return convertView;
    }
}
