package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class ServerResponse {
    private String result;
    private String message;
    private String phanquyen;
    private String TenNv;
    private String MaNv;
    private String Diachi;
    private String Sdt;

    private Thongtinoder thongtinoder;

    public Thongtinoder getThongtinoder() {
        return thongtinoder;
    }

    public String getTenNv() {
        return TenNv;
    }

    public String getMaNv() {
        return MaNv;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getSdt() {
        return Sdt;
    }

    private User1 user1;
    private User user;





    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
    public String getPhanquyen() {
        return phanquyen;
    }


    public User1 getUser1() {
        return user1;
    }
    public User getUser() {
        return user;
    }


}

