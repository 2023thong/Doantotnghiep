package gmail.com.qlcafepoly.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Nhanvienht extends BaseAdapter {
    private List<User> usernv;
    private LayoutInflater inflater;

    public Nhanvienht(Context context, List<User> usernv) {
        this.usernv = usernv;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return usernv.size();
    }

    @Override
    public Object getItem(int position) {
        return usernv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_nhan_vien, parent, false);
        }
        User user = usernv.get(position);

        TextView tvManv = convertView.findViewById(R.id.tvMaNv);
        TextView tvTennv = convertView.findViewById(R.id.tvTennv);
        TextView tvTendn = convertView.findViewById(R.id.tvTenDn);
        TextView tvMatkhau = convertView.findViewById(R.id.tvMatkhau);
        TextView tvSdt = convertView.findViewById(R.id.tvSdt);
        TextView tvDiachi = convertView.findViewById(R.id.tvDiachi);
        TextView tvChucvu = convertView.findViewById(R.id.tvChucvu);


        tvManv.setText(user.getMaNv());
        tvTennv.setText(user.getTenNv());
        tvTendn.setText(user.getTenDn());
        tvMatkhau.setText(user.getMatkhau());
        tvSdt.setText(user.getSdt());
        tvDiachi.setText(user.getDiachi());
        tvChucvu.setText(user.getChucvu());


        return convertView;
    }
}
