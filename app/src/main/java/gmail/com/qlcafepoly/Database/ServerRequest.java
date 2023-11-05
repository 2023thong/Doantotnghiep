package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.LoaiHang;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import gmail.com.qlcafepoly.admin.User2;

public class ServerRequest {

        private String operation;
        private User user;

        private User1 user1;
        private User2 user2;

        private Menu menu;
        private LoaiHang loaihang;

    public void setLoaihang(LoaiHang loaihang) {
        this.loaihang = loaihang;
    }

    public void setUser2(User2 user2) {
        this.user2 = user2;
    }

    public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setUser(User user) {
            this.user = user;
        }
        public void setUser1(User1 user1) {
            this.user1 = user1;
        }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }



}
