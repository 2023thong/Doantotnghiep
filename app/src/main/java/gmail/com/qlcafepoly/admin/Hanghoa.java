package gmail.com.qlcafepoly.admin;

public class Hanghoa {
    private  String MaHH;
    private String MaNcc;
    private String TenLh;
    private String TenHh;
    private int GiaSp;
    private String Ghichu;
    private String imagePath;

    public Hanghoa(String maHH, String maNcc, String tenLh, String tenHh, int giaSp, String ghichu) {
        MaHH = maHH;
        MaNcc = maNcc;
        TenLh = tenLh;
        TenHh = tenHh;
        GiaSp = giaSp;
        Ghichu = ghichu;
    }

    public Hanghoa(String maHH, String imagePath) {
        MaHH = maHH;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getTenLh() {
        return TenLh;
    }

    public void setTenLh(String tenLh) {
        TenLh = tenLh;
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
