package hcmute.edu.vn.store.utils;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.store.model.GioHang;
import hcmute.edu.vn.store.model.User;

public class Utils {
    public static final String BASE_URL="http://192.168.1.16/banhang/";

    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static  String ID_RECEIVED;
    public static final String SENDID = "idsend";           //người gửi
    public static final String RECEIVEDID = "idreceived";   //người nhận
    public static final String MESS = "message";            //nội dung
    public static final String DATETIME = "datetime";       //ngày
    public static final String PATH_CHAT = "chat";
}
