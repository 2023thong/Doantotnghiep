package gmail.com.qlcafepoly.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper (Context context){
        super(context,"QUANLYCAPHE", null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng
        //0: chưa trả tiền
        //1: đã trả tiền
        String ban = "CREATE TABLE BAN(MaBn varchar primary key autoincrement, TenBan varchar, Trangthai varchar)";
        db.execSQL(ban);

        String calam = "CREATE TABLE CALAM(MaCL varchar primary key autoincrement, TenCL varchar, GioBd datetime, GioKt datetime, Sotien int, MaNv varchar references NHANVIEN(MaNV))";
        db.execSQL(calam);

        String hanghoa = "CREATE TABLE HANGHOA(MaHH varchar primary key autoincrement, MaNCC varchar references NHACUNGCAP(MaNCC), MaLH varchar references LOAIHANG(MaLH), TenHH varchar, GiaSP int, GhiChu varchar)";
        db.execSQL(hanghoa);

        String hoadon = "CREATE TABLE HOADON(MaHD varchar primary key autoincrement, MaNV varchar references NHANVIEN(MaNV), MaBn varchar references BAN(MaBn), TenKH text, TenLH varchar, GiaTien int, Trangthai varchar, ThoiGian date, MaOder varchar references ODER(MaOder))";
        db.execSQL(hoadon);

        String hoadonnhaphang ="CREATE TABLE HOADONNHAPHANG(MaHDNH c, TenNCC text, MaNV varchar references NHANVIEN(MaNV), ThoiGian date, TenMH varchar, Soluong int, TongTien int, GhiChu varchar, MaHH varchar references HANGHOA(MaHH))";
        db.execSQL(hoadonnhaphang);

        String nhanvien = "CREATE TABLE NHANVIEN(MaNV varchar primary key autoincrement, TenNV text, TenDN varchar, MatKhau varchar, Sdt int, DiaChi varchar, ChucVu text)";
        db.execSQL(nhanvien);
        //Data mẫu nhân viên
        db.execSQL("INSERT INTO NHANVIEN VALUES(1,'Lê Đức Tú','tu1',123,0364183310,'danang',1");

        String menu = "CREATE TABLE MENU(MaMn varchar primary key autoincrement, TenLH varchar, GiaTien int)";
        db.execSQL(menu);

        String nhacungcap = "CREATE TABLE NHACUNGCAP(MaNCC varchar primary key autoincrement, TenNCC varchar, DiaChi varchar, Sdt int)";
        db.execSQL(nhacungcap);

        String oder = "CREATE TABLE ODER(MaOder varchar primary key autoincrement, TenLH varchar, SoLuong int, GiaTien int, MaBn varchar references BAN(MaBn), MaMn references MENU(MaMn))";
        db.execSQL(oder);

        String loaihang = "CREATE TABLE LOAIHANG(MaLH varchar primary key autoincrement, TenLH varchar, GhiChu varchar)";
        db.execSQL(loaihang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
