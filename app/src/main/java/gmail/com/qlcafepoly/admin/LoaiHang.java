package gmail.com.qlcafepoly.admin;

public class LoaiHang {
    private  String TenLh;
    private String Ghichu;

    public LoaiHang() {
    }

    public String getTenLh() {
        return TenLh;
    }

    public void setTenLh(String tenLh) {
        TenLh = tenLh;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }

    public LoaiHang(String tenLh, String ghichu) {
        TenLh = tenLh;
        Ghichu = ghichu;
    }
}
