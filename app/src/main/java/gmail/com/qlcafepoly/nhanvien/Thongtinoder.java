package gmail.com.qlcafepoly.nhanvien;

import java.io.Serializable;

public class Thongtinoder implements Serializable {
    private int MaOder;
    private String MaBn;

    private String formattedTongtien;
    int tratien;
    private int TrangThai;
    private String ngay;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
// Getter và setter cho Trangthai




    public void setTratien(int tratien) {
        this.tratien = tratien;
    }

    public int getMaOder() {
        return MaOder;
    }

    public void setMaOder(int maOder) {
        MaOder = maOder;
    }

    public String getMaBn() {
        return MaBn;
    }

    public void setMaBn(String maBn) {
        MaBn = maBn;
    }


    private int TongTien;

    public int getTratien() {
        return tratien;
    }

    public String getFormattedTongtien() {
        return formattedTongtien;
    }

    public void setFormattedTongtien(String formattedTongtien) {
        this.formattedTongtien = formattedTongtien;
    }

     // assuming quantity is an attribute in Thongtinoder

    // ... other methods and constructors
}