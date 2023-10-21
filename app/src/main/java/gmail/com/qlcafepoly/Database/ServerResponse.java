package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;

public class ServerResponse {
    private String result;
    private String message;
    private String phanquyen;
    private String MaNv;

    private User1 user1;
    private User user;

    private String TenNv;
    private String Sdt;
    private String Diachi;

    public String getTenNv() {
        return TenNv;
    }

    public String getSdt() {
        return Sdt;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getMaNv() {
        return MaNv;
    }

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

