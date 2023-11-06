package gmail.com.qlcafepoly.Database;

import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
<<<<<<< HEAD
=======
import gmail.com.qlcafepoly.model.Ban;
import gmail.com.qlcafepoly.model.Menu;
>>>>>>> 61ce38818051f51f2ee8b80b82a3cef4b0bb4a1e

public class ServerRequest {

        private String operation;
        private User user;
        private Ban ban;
        private User1 user1;




        public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setUser(User user) {
            this.user = user;
        }
        public void setUser1(User1 user1) {
            this.user1 = user1;
        }

    public void setBan(Ban ban) {
        this.ban = ban;
    }



}
