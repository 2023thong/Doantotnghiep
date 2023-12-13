package gmail.com.qlcafepoly.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gmail.com.qlcafepoly.nhanvien.Oder;

public class OderUtils {
    public static List<Oder> convertNgayFormat(List<Oder> odersList) {
        List<Oder> updatedList = new ArrayList<>();
        SimpleDateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (Oder oder : odersList) {
            try {
                Date ngayDate = sourceFormat.parse(oder.getNgay());
                String formattedNgay = targetFormat.format(ngayDate);
                oder.setNgay(formattedNgay);
                updatedList.add(oder);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle parsing exception if needed
            }
        }

        return updatedList;
    }
}
