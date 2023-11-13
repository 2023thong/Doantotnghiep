package gmail.com.qlcafepoly.admin;

public class Menu {
    private String MaMn;
    private String TenDu;
    private int Giatien;

    public Menu() {
    }

    public Menu(String maMn, String TenDu, int giatien) {
        MaMn = maMn;
        TenDu = TenDu;
        Giatien = giatien;
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
}
