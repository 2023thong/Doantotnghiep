package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.LoaiHang;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import gmail.com.qlcafepoly.model.Ban;
>>>>>>> 61ce38818051f51f2ee8b80b82a3cef4b0bb4a1e
=======
import gmail.com.qlcafepoly.admin.User2;
>>>>>>> cf972eb8fde6d07965cdea23e836e51599f5399a

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

<<<<<<< HEAD
=======
    public User2 getUser2() {
        return user2;
    }
>>>>>>> cf972eb8fde6d07965cdea23e836e51599f5399a

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

    private Ban ban;

    public Ban getBan() {
        return ban;
    }
}

