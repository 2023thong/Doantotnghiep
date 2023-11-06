package gmail.com.qlcafepoly.Database;


import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
<<<<<<< HEAD
=======
import gmail.com.qlcafepoly.model.Ban;
>>>>>>> 61ce38818051f51f2ee8b80b82a3cef4b0bb4a1e
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




        public void setOperation(String operation) {
            this.operation = operation;
        }


        public void setUser1(User1 user1) {
            this.user1 = user1;
        }
        public void setUser(User user) {
            this.user = user;
        }
        public void setBan(Ban ban) {
            this.ban = ban;
        }
    }
}

