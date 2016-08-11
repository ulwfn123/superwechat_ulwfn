package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2016/8/11.
 */
public class BuyActivity extends BaseActivity {
    BuyActivity mContext;
    EditText edOrderName;
    EditText edOrderPhone;
    EditText edOrderProvince;
    EditText edOrderStreet;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        setContentView(R.layout.activity_buy);
        initView();

    }

    private void initView() {
        edOrderName = (EditText) findViewById(R.id.et_order_Name);
        edOrderPhone = (EditText) findViewById(R.id.et_order_Phonee);
        edOrderProvince = (EditText) findViewById(R.id.et_order_Provnce);
        edOrderStreet = (EditText) findViewById(R.id.et_order_Street);

    }

}
