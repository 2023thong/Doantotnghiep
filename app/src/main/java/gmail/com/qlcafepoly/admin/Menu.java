package gmail.com.qlcafepoly.admin;

public class Menu {
    private String MaMn;
    private String TenDu;
    private int Giatien;

    private int soluong;
    private int Giatientd;
    private int MaOder;

    public int getMaOder() {
        return MaOder;
    }

    public void setMaOder(int maOder) {
        MaOder = maOder;
    }

    public Menu() {
    }
    public Menu(String maMn, String tenDu, int giatien , int soluong, int maOder) {
        MaMn = maMn;
        TenDu = tenDu;
        Giatien = giatien;
        MaOder = maOder;
        this.soluong = soluong;
        this.Giatientd = giatien * soluong;
    }

    public int getSoluong() {
        return soluong;
    }



    public String getMaMn() {
        return MaMn;
    }

    public void setMaMn(String maMn) {
        MaMn = maMn;
    }

    public String getTenDu() {
        return TenDu;
    }

    public void setTenDu(String tenDu) {
        TenDu = tenDu;
    }

    public int getGiatien() {
        return Giatien;
    }

    public void setGiatien(int giatien) {
        Giatien = giatien;
    }
    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    public int getGiatientd() {
        return Giatientd;
    }
    public void setGiatientd(int soluong) {
        this.Giatientd = this.Giatien * soluong;
    }
    public int calculateTotalPrice() {
        return Giatien * soluong;
    }
}
