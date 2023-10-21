package gmail.com.qlcafepoly.admin;

public class User {
    private String TenDn;
    private String Matkhau;

    private String MaNv;
    private String TenNv;
    private String Sdt;
    private String Diachi;


    public User() {

    }

    public String getMaNv() {
        return MaNv;
    }

    public void setMaNv(String maNv) {
        MaNv = maNv;
    }

    public String getTenNv() {
        return TenNv;
    }

    public void setTenNv(String tenNv) {
        TenNv = tenNv;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getTenDn() {
        return TenDn;
    }


    public void setTenDn(String tenDn) {
        TenDn = tenDn;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }
}
