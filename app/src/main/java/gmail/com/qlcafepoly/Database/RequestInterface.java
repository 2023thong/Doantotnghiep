package gmail.com.qlcafepoly.Database;


import gmail.com.qlcafepoly.User;
import gmail.com.qlcafepoly.User1;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("learn-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);


    class ServerRequest {
        private String operation;


        private User1 user1;
        private User user;




        public void setOperation(String operation) {
            this.operation = operation;
        }


        public void setUser1(User1 user1) {
            this.user1 = user1;
        }
        public void setUser(User user) {
            this.user = user;
        }


    }
}

