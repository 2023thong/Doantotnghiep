package gmail.com.qlcafepoly.model;

public class orderModel {
    private int TongTien;

    public orderModel(int tongTien) {
        TongTien = tongTien;
    }

    public orderModel() {
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
}
