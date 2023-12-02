//package gmail.com.qlcafepoly.nhanvien;
//
//import static com.google.gson.internal.bind.util.ISO8601Utils.format;
//
//import android.app.AlarmManager;
//import android.app.IntentService;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class XoaTheoNgay extends IntentService {
//
//    private static final String TAG = "XoaTheoNgay";
//    private static final String ACTION_DELETE_DATA = "gmail.com.qlcafepoly.nhanvien.action.DELETE_DATA";
//    private static final String EXTRA_TTODER = "ttoder";
//    private java.text.SimpleDateFormat SimpleDateFormat;
//
//    public XoaTheoNgay() {
//        super("XoaTheoNgay");
//    }
//
//    public static void setupDailyAlarm(Context context, List<Thongtinoder> ttoder) {
//        Intent intent = new Intent(context, XoaTheoNgay.class);
//        intent.setAction(ACTION_DELETE_DATA);
//        intent.putExtra(EXTRA_TTODER, String.valueOf(ttoder));
//
//        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 0);
//
//        // Set up the repeating alarm
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        } else {
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);
//        }
//
//        Log.d(TAG, "Alarm set up");
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_DELETE_DATA.equals(action)) {
//                handleDeleteData(intent.getStringExtra(EXTRA_TTODER));
//            }
//        }
//    }
//
//    private void handleDeleteData(String ttoderString) {
//        // Convert the ttoderString back to a List<Thongtinoder> if needed
//        List<Thongtinoder> ttoder = // Convert ttoderString to List<Thongtinoder>
//
//                // Get the current date
//                SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        String currentDate = format(new Date());
//
//        // Create an instance of Pay1 adapter
//        Pay1 adapter = new Pay1(getApplicationContext(), ttoder);
//
//        // Call the method to delete all data based on the current date
//        adapter.xoaToanBoDuLieuTheoNgay(currentDate);
//    }
//}
