package gmail.com.qlcafepoly;

public class Hanghoa {
    private  String MaHH;
    private String MaNcc;
    private String MaLh;
    private String TenHh;
    private int GiaSp;
    private String Ghichu;

    public Hanghoa(String maHH, String maNcc, String maLh, String tenHh, int giaSp, String ghichu) {
        MaHH = maHH;
        MaNcc = maNcc;
        MaLh = maLh;
        TenHh = tenHh;
        GiaSp = giaSp;
        Ghichu = ghichu;
    }

    public Hanghoa() {
    }

    public String getMaHH() {
        return MaHH;
    }

    public void setMaHH(String maHH) {
        MaHH = maHH;
    }

    public String getMaNcc() {
        return MaNcc;
    }

    public void setMaNcc(String maNcc) {
        MaNcc = maNcc;
    }

    public String getMaLh() {
        return MaLh;
    }

    public void setMaLh(String maLh) {
        MaLh = maLh;
    }

    public String getTenHh() {
        return TenHh;
    }

    public void setTenHh(String tenHh) {
        TenHh = tenHh;
    }

    public int getGiaSp() {
        return GiaSp;
    }

    public void setGiaSp(int giaSp) {
        GiaSp = giaSp;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }
}
