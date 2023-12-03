package gmail.com.qlcafepoly.admin;

import java.util.List;

public class DataHolder {
    private static List<User1> lsuList;

    public static List<User1> getLsuList() {
        return lsuList;
    }

    public static void setLsuList(List<User1> lsuList) {
        DataHolder.lsuList = lsuList;
    }
}
