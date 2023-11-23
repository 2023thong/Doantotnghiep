package gmail.com.qlcafepoly.nhanvien;

public class Oder {
    private String MaBn;
    private String TongTien;
    private String MaMn;
    private String TrangThai;
    private String TenDu;
    private String Soluong;
    private String Giatien;
    private String MaOder;
    private String Ngay;

    public Oder(String maBn, String tongTien, String maMn, String trangThai, String tenDu, String soluong, String giatien, String maOder, String ngay) {
        MaBn = maBn;
        TongTien = tongTien;
        MaMn = maMn;
        TrangThai = trangThai;
        TenDu = tenDu;
        Soluong = soluong;
        Giatien = giatien;
        MaOder = maOder;
        Ngay = ngay;
    }
    public String sumTongtien() {
        return TongTien;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public Oder() {
    }

    public String getMaOder() {
        return MaOder;
    }

    public void setMaOder(String maOder) {
        MaOder = maOder;
    }

    public String getMaBn() {
        return MaBn;
    }

    public void setMaBn(String maBn) {
        MaBn = maBn;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongtien) {
        TongTien = tongtien;
    }

    public String getMaMn() {
        return MaMn;
    }

    public void setMaMn(String maMn) {
        MaMn = maMn;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getTenDu() {
        return TenDu;
    }

    public void setTenDu(String tenDu) {
        TenDu = tenDu;
    }

    public String getSoluong() {
        return Soluong;
    }

    public void setSoluong(String soluong) {
        Soluong = soluong;
    }

    public String getGiatien() {
        return Giatien;
    }

    public void setGiatien(String giatien) {
        Giatien = giatien;
    }
}
