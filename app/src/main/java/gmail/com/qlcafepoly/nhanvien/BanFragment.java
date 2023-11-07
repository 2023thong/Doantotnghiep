package gmail.com.qlcafepoly.nhanvien;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;
import me.relex.circleindicator.CircleIndicator3;


public class BanFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<photo> mListPhoto;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mViewPager2.getCurrentItem();
            if (currentPosition == mListPhoto.size() - 1 ){
                mViewPager2.setCurrentItem(0);
            }else {
                mViewPager2.setCurrentItem(currentPosition + 1);
            }
        }
    };

    LinearLayout luanh1, luanh2;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_ban_fragment, container, false);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        mViewPager2 = view.findViewById(R.id.VP2);
        mCircleIndicator3 = view.findViewById(R.id.CI3);

        mViewPager2.setOffscreenPageLimit(5);
        mViewPager2.setClipToPadding(false);
        mViewPager2.setClipChildren(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(45));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(1f + r * 0.25f);
                page.setScaleX(1f + r * 0.25f);
            }
        });
        compositePageTransformer.addTransformer(new DepthPageTransformer());
        mViewPager2.setPageTransformer(compositePageTransformer);

        mListPhoto = getListPhoto();
        PhotoA photoA = new PhotoA(mListPhoto);
        mViewPager2.setAdapter(photoA);
        mCircleIndicator3.setViewPager(mViewPager2);

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }
        });


        luanh1 = view.findViewById(R.id.luAnh1);
        luanh2 = view.findViewById(R.id.luAnh2);

        luanh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhsachFragment danhsachFragment = new DanhsachFragment();
                manager.beginTransaction().replace(R.id.frag2,danhsachFragment).commit();
            }
        });
        luanh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhsachFragment danhsachFragment = new DanhsachFragment();
                manager.beginTransaction().replace(R.id.frag2,danhsachFragment).commit();
            }
        });
        return view;
    }



    private List<photo> getListPhoto(){
        List<photo> list = new ArrayList<>();
        list.add(new photo(R.drawable.nuoc5));
        list.add(new photo(R.drawable.nuoc6));
        list.add(new photo(R.drawable.nuoc7));
        list.add(new photo(R.drawable.nuoc8));
        list.add(new photo(R.drawable.nuoc9));
        list.add(new photo(R.drawable.nuoc7));


        return list;
    }
    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private float MIN_SCALE = 0.8f;

        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();

            if(position < - 1.5){
                page.setAlpha(0f);
            }else if (position <= 0){
                page.setAlpha(1f);
                page.setTranslationX(0f);
                page.setScaleX(1f);
                page.setScaleY(1f);
            } else if (position <= 1){
                page.setAlpha(1 - position);
                page.setTranslationX(pageWidth * -position);

                float scaleFactor = MIN_SCALE = (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }else {
                page.setAlpha(0f);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 3000);
    }
}