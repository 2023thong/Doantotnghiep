package gmail.com.qlcafepoly.admin;

public class User {

    private String TenDn;
    private String Matkhau;
    private String passwordcu;
    private String passwordmoi;
    private String MaNv;
    private String TenNv;

    private String Sdt;
    private String Diachi;
    private int Chucvu;

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

    public String getPasswordcu() {
        return passwordcu;

    }

    public User(int chucvu) {
        Chucvu = chucvu;
    }

    public int getChucvu() {
        return Chucvu;
    }

    public void setChucvu(int chucvu) {
        Chucvu = chucvu;
    }

    public User() {


    }

    public void setPasswordcu(String passwordcu) {
        this.passwordcu = passwordcu;
    }

    public String getPasswordmoi() {
        return passwordmoi;
    }

    public void setPasswordmoi(String passwordmoi) {
        this.passwordmoi = passwordmoi;
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

