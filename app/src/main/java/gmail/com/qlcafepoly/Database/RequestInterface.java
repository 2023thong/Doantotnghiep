package gmail.com.qlcafepoly.Database;


import gmail.com.qlcafepoly.admin.LoaiHang;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import gmail.com.qlcafepoly.admin.User2;
import gmail.com.qlcafepoly.model.Ban;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("duantotnghiep/")
    Call<ServerResponse> operation(@Body ServerRequest request);


    class ServerRequest {
        private String operation;


        private User1 user1;
        private User user;
        private Ban ban;

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


        public void setUser1(User1 user1) {
            this.user1 = user1;
        }
        public void setUser(User user) {
            this.user = user;
        }
        public void setMenu(Menu menu) {
            this.menu = menu;
        }
        public void setBan(Ban ban) {
            this.ban = ban;
        }


    }
}