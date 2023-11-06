package gmail.com.qlcafepoly.admin;

public class Menu {
    private String MaMn;
    private String TenLh;
    private int Giatien;

    public Menu() {
    }

    public Menu(String maMn, String tenLh, int giatien) {
        MaMn = maMn;
        TenLh = tenLh;
        Giatien = giatien;
    }

    public String getMaMn() {
        return MaMn;
    }

    public void setMaMn(String maMn) {
        MaMn = maMn;
    }

    public String getTenLh() {
        return TenLh;
    }

    public void setTenLh(String tenLh) {
        TenLh = tenLh;
    }

    public int getGiatien() {
        return Giatien;
    }

    public void setGiatien(int giatien) {
        Giatien = giatien;
    }
}
