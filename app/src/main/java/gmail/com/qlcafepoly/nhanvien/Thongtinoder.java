package gmail.com.qlcafepoly.nhanvien;

import java.io.Serializable;

public class Thongtinoder implements Serializable {
    private int MaOder;
    private String MaBn;
    private String formattedTongtien; // Thêm thuộc tính mới
    int tratien;
    private int Trangthai;


    // Getter và setter cho Trangthai
    public int getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(int trangthai) {
        Trangthai = trangthai;
    }

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

    public int getTongtien() {
        return Tongtien;
    }

    public void setTongtien(int tongtien) {
        Tongtien = tongtien;
    }

    private int Tongtien;

    public int getTratien() {
        return tratien;
    }

    public String getFormattedTongtien() {
        return formattedTongtien;
    }

    public void setFormattedTongtien(String formattedTongtien) {
        this.formattedTongtien = formattedTongtien;
    }
}
