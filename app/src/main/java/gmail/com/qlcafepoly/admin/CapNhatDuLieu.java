package gmail.com.qlcafepoly.admin;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class CapNhatDuLieu extends Service {
    private Handler handler;
    private Runnable dataUpdater;
    private static final long UPDATE_INTERVAL = 60000; // 60 giây (thay đổi theo nhu cầu của bạn)

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        dataUpdater = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Khởi động việc cập nhật dữ liệu ban đầu khi dịch vụ được khởi động
        handler.post(dataUpdater);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dừng cập nhật dữ liệu khi dịch vụ bị hủy
        handler.removeCallbacks(dataUpdater);
    }
}
