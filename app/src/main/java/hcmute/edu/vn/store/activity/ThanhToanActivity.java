package hcmute.edu.vn.store.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.store.R;
import hcmute.edu.vn.store.model.NotiSendData;
import hcmute.edu.vn.store.retrofit.ApiBanHang;
import hcmute.edu.vn.store.retrofit.ApiPushNofication;
import hcmute.edu.vn.store.retrofit.RetrofitClient;
import hcmute.edu.vn.store.retrofit.RetrofitClientNoti;
import hcmute.edu.vn.store.utils.Utils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang;
    long tongtien;
    int totalItem;
    int iddonhang;

//    private String amount = "10000";
//    private String fee = "0";   //developer default
//    int environment = 0;//developer default
//    private String merchantName = "HoangNgoc";
//    private String merchantCode = "MOMOC2IC20220510";
//    private String merchantNameLabel = "Lezada";
//    private String description = "Mua Hàng Online";


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
//        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION, môi trường phát triển
        initView();
        countItem();
        initControl();
    }

    private void countItem() {
        totalItem=0;
        for(int i=0; i<Utils.mangmuahang.size();i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }
//
//    //Get token through MoMo app
//    private void requestPayment(String iddonhang) {
//        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
//        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//
//        Map<String, Object> eventValue = new HashMap<>();
//        //client Required
//        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
//        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
//        eventValue.put("amount", amount); //Kiểu integer
//        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
//        eventValue.put("orderLabel", iddonhang); //gán nhãn
//
//        //client Optional - bill info
//        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
//        eventValue.put("fee", "0"); //Kiểu integer
//        eventValue.put("description", description); //mô tả đơn hàng - short description
//
//        //client extra data
//        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
//        eventValue.put("partnerCode", merchantCode);
//        //Example extra data
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//        eventValue.put("extra", "");
//        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
//
//
//    }
//    //Get token callback from MoMo app an submit to server side
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
//            if(data != null) {
//                if(data.getIntExtra("status", -1) == 0) {
//                    //TOKEN IS AVAILABLE
//                    Log.d("Thành Công", data.getStringExtra("message"));
//                    String token = data.getStringExtra("data"); //Token response
//
//                    compositeDisposable.add(apiBanHang.updateMomo(iddonhang, token)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                    messageModel -> {
//                                        if (messageModel.isSuccess()){
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    },
//                                    throwable -> {
//                                        Log.d("error", throwable.getMessage());
//                                    }
//                            ));
//
//                    String phoneNumber = data.getStringExtra("phonenumber");
//                    String env = data.getStringExtra("env");
//                    if(env == null){
//                        env = "app";
//                    }
//
//                    if(token != null && !token.equals("")) {
//                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
//                        // IF Momo topup success, continue to process your order
//                    } else {
//                        Log.d("thanhcong", "Không Thành Công");
//                    }
//                } else if(data.getIntExtra("status", -1) == 1) {
//                    //TOKEN FAIL
//                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
//                    Log.d("thanhcong", "Không Thành Công");
//                } else if(data.getIntExtra("status", -1) == 2) {
//                    //TOKEN FAIL
//                    Log.d("thanhcong", "Không Thành Công");
//                } else {
//                    //TOKEN FAIL
//                    Log.d("thanhcong", "Không Thành Công");
//                }
//            } else {
//                Log.d("thanhcong", "Không Thành Công");
//            }
//        } else {
//            Log.d("thanhcong", "Không Thành Công");
//        }
//    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien",0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText((Utils.user_current.getEmail()));
        txtsodt.setText(Utils.user_current.getMobile());
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext() , "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }
                else{
//                    postdata
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("Test", new Gson().toJson(Utils.manggiohang));
                    compositeDisposable.add(apiBanHang.createOder(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem,new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {

                                        Toast.makeText(getApplicationContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }



                            ));




                }
            }
        });
    }




//    private void pushNotiToUser() {
//        //gettoken
//        compositeDisposable.add(apiBanHang.gettoken(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//
//                        userModel -> {
//                            if(userModel.isSuccess()){
//                                for(int i=0; i<userModel.getResult().size(); i++){
//                                    Map<String, String> data = new HashMap<>();
//                                    data.put("title","thongbao");
//                                    data.put("body","Ban co don hang moi");
//                                    NotiSendData notiSendData = new NotiSendData(userModel.getResult().get(i).getToken(), data);
//                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
//                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(
//                                                    notiRespone -> {
//
//                                                    },
//                                                    throwable -> {
//                                                        Log.d("logg",throwable.getMessage());
//                                                    }
//                                            ));
//                                }
//                            }
//
//                        },
//                        throwable -> {
//                            Log.d("logg", throwable.getMessage());
//                        }
//                ));
//
//    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar);
        txttongtien = findViewById(R.id.txttongtien);
        txtsodt = findViewById(R.id.txtsodienthoai);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang=findViewById(R.id.btndathang);
//        btnmomo=findViewById(R.id.btnmomo);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}