package gmail.com.qlcafepoly.Database;


import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.admin.User1;
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
        private Thongtinoder thongtinoder;

        public void setThongtinoder(Thongtinoder thongtinoder) {
            this.thongtinoder = thongtinoder;
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


    }
}

