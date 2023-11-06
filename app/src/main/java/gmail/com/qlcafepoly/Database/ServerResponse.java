package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.LoaiHang;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import gmail.com.qlcafepoly.admin.User2;

public class ServerResponse {
    private String result;
    private String message;
    private String phanquyen;

    private String MaNv;

    private User1 user1;
    private User user;

    private User2 user2;

    private Menu menu;

    private LoaiHang loaihang;

    public LoaiHang getLoaihang() {
        return loaihang;
    }


    private String TenNv;


    private String Diachi;
    private String Sdt;

    public User2 getUser2() {
        return user2;
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
    public Menu getMenu() {
        return menu;
    }


}

