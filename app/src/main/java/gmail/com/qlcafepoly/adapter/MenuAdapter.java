package gmail.com.qlcafepoly.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Menu;

public class MenuAdapter  extends ArrayAdapter<Menu> {
        Activity context;
        int IdLayout;
        ArrayList<Menu> mnlist;

        public MenuAdapter(Activity context, int idLayout, ArrayList<Menu> mnlist) {
                super(context, idLayout, mnlist);
                this.context = context;
                IdLayout = idLayout;
                this.mnlist = mnlist;
        }

//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                LayoutInflater mninflater = context.getLayoutInflater();
//                convertView = mninflater.inflate(IdLayout, null);
//                Menu menu = mnlist.get(position);
//                ImageView img_mn = convertView.findViewById(R.id.img);
        }

