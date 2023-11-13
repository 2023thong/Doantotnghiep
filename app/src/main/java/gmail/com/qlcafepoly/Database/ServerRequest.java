package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class ServerRequest {

        private String operation;
        private User user;

        private User1 user1;

    private Thongtinoder thongtinoder;

    public void setThongtinoder(Thongtinoder thongtinoder) {
        this.thongtinoder = thongtinoder;
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



}
