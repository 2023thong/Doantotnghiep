package gmail.com.qlcafepoly.api;

import java.util.List;

import gmail.com.qlcafepoly.model.DoUongModel;

public class MenuResponse {
    private List<DoUongModel> menu;
    private int success;
    public List<DoUongModel> getMenu() {
        return menu;
    }

    public int getSuccess() {
        return success;
    }
}
