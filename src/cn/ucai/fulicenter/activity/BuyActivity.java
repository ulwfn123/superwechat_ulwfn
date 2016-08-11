package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.view.DisplayUtils;

/**
 * Created by Administrator on 2016/8/11.
 */
public class BuyActivity extends BaseActivity implements PaymentHandler{
    private static String URL = "http://218.244.151.190/demo/charge";
    BuyActivity mContext;
    EditText edOrderName;
    EditText edOrderPhone;
    EditText edOrderProvince;
    EditText edOrderStreet;
    Button mBtnbuy;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        setContentView(R.layout.activity_buy);
        initView();
        setListener();

        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb", "jdpay_wap"});

        // 提交数据的格式，默认格式为json
        // PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        PingppLog.DEBUG = true;
    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            /**
             * code：支付结果码  -2:服务端错误、 -1：失败、 0：取消、1：成功
             * error_msg：支付结果信息
             */
            if (data.getExtras().getInt("code") != 2) {
                PingppLog.d(data.getExtras().getString("result") + " " + data.getExtras());
            } else {
                String result = data.getExtras().getString("result");
                try {
                    final JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("error")) {
                        result = jsonObject.optJSONObject("error").toString();
                    } else {
                        result = jsonObject.optJSONObject("success").toString();
                    }
                    PingppLog.d("result::"+result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void setListener() {
        mBtnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edOrderName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    edOrderName.setError("姓名不能为空");
                    edOrderName.requestFocus();// 请求焦点
                    return;
                }
                final String phone = edOrderPhone.getText().toString();
                if (TextUtils.isEmpty(phone)|| phone.matches("[\\d]{11}")) {
                    edOrderPhone.setError("手机号码11位且不能为空");
                    edOrderPhone.requestFocus();// 请求焦点
                    return;
                }
                final String Province = edOrderProvince.getText().toString();
                if (TextUtils.isEmpty(Province)) {
                    edOrderProvince.setError("省份不能为空");
                    edOrderProvince.requestFocus();// 请求焦点
                    return;
                }
                final String Street = edOrderStreet.getText().toString();
                if (TextUtils.isEmpty(Street)) {
                    edOrderStreet.setError("地址不能为空");
                    edOrderStreet.requestFocus();// 请求焦点
                    return;
                }
                gotoStatements();
            }
        });
    }
    // 引用 第三方的方法，创建支付通道
    private void gotoStatements() {
        //生成订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // 计算总金额（以分为单位）
        int amount = 0;
        JSONArray billList = new JSONArray();
        final List<CartBean> cartList = FuliCenterApplication.getInstance().getCartList();
        for (CartBean cart : cartList) {
            final GoodDetailsBean goods = cart.getGoods();
            if (goods != null && cart.isChecked()) {
                amount += convertPrice(goods.getRankPrice()) * cart.getCount();
                billList.put(goods.getGoodsName() + "x" + cart.getCount());
            }
        }
        //构建中的json 对象
        final JSONObject bill = new JSONObject();

        //自定义的额外信息 ，
        final JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            bill.put("order_no", orderNo);
            bill.put("amount", amount);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 壹收款，创建 支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(),bill.toString(),URL,this);

    }
    private int convertPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }


    private void initView() {
        DisplayUtils.initbackWithTitle(mContext,"填写收货地址");
        edOrderName = (EditText) findViewById(R.id.et_order_Name);
        edOrderPhone = (EditText) findViewById(R.id.et_order_Phonee);
        edOrderProvince = (EditText) findViewById(R.id.et_order_Provnce);
        edOrderStreet = (EditText) findViewById(R.id.et_order_Street);
        mBtnbuy = (Button) findViewById(R.id.btn_buy);
    }


}
