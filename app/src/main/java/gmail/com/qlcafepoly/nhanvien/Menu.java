package gmail.com.qlcafepoly.nhanvien;

public class Menu {
    private int image;
    private String name;
    private int gia;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Menu(int image, String name, int gia) {
        this.image = image;
        this.name = name;
        this.gia = gia;
    }
}
