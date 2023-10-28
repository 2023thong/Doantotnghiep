package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Unpaid1 extends BaseAdapter {
    private List<Menu1> menu2;
    private LayoutInflater inflater;

    public Unpaid1(Context context, List<Menu1> menu2) {
        this.menu2 = menu2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menu2.size();
    }

    @Override
    public Object getItem(int position) {
        return menu2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_unpaid, parent, false);


        }
        Menu1 menu1 = menu2.get(position);


        TextView tvmb = convertView.findViewById(R.id.tvbantrong);

        tvmb.setText(menu1.getMaBn());


        return convertView;
    }
}

