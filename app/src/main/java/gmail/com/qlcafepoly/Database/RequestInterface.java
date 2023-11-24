package gmail.com.qlcafepoly.Database;


import gmail.com.qlcafepoly.admin.LoaiHang;
import gmail.com.qlcafepoly.admin.Menu;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;

import gmail.com.qlcafepoly.admin.User2;
import gmail.com.qlcafepoly.model.Ban;
import gmail.com.qlcafepoly.nhanvien.Oder;

import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

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
        private Oder oder1;
        private Menu menu;

        private Thongtinoder thongtinoder;


        public void setThongtinoder(Thongtinoder thongtinoder) {
            this.thongtinoder = thongtinoder;
        }


        private LoaiHang loaihang;



        public void setOder1(Oder oder1) {
            this.oder1 = oder1;
        }

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