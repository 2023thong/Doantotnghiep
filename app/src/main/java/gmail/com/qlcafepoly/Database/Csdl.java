package gmail.com.qlcafepoly.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Csdl extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "duan";
    private static final int DATABASE_VERSION = 1;

    public Csdl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng trong CSDL
        String NHANVIEN = "CREATE TABLE `hanghoa` (\n" +
                "  `MaHH` varchar(20) NOT NULL,\n" +
                "  `MaNcc` varchar(20) NOT NULL,\n" +
                "  `MaLh` varchar(20) NOT NULL,\n" +
                "  `TenHh` varchar(20) NOT NULL,\n" +
                "  `GiaSp` int(20) NOT NULL,\n" +
                "  `Ghichu` varchar(50) NOT NULL\n" +
                ")";
        db.execSQL(NHANVIEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cập nhật CSDL nếu cần
        db.execSQL("DROP TABLE IF EXISTS hanghoa");
        onCreate(db);
    }
    public long addData(String mahh, String mancc, String malh, String tenhh, String ghichu, int giatien) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("MaHH", mahh);
        values.put("MaNcc", mancc);
        values.put("MaLh", malh);
        values.put("TenHh", tenhh);
        values.put("GiaSp", giatien);
        values.put("Ghichu", ghichu);

        // Lưu dữ liệu vào CSDL
        long newRowId = database.insert("my_table", null, values);

        database.close();

        return newRowId;
    }
}

