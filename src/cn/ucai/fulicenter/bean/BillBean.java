package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/11.
 */
public class BillBean implements Serializable {
    private String ordeName;
    private String ordePhone;
    private String ordeProvince;
    private String ordeStreet;

    public BillBean(String ordeName, String ordePhone, String ordeProvince, String ordeStreet) {
        this.ordeName = ordeName;
        this.ordePhone = ordePhone;
        this.ordeProvince = ordeProvince;
        this.ordeStreet = ordeStreet;
    }

    public String getOrdeName() {
        return ordeName;
    }

    public void setOrdeName(String ordeName) {
        this.ordeName = ordeName;
    }

    public String getOrdePhone() {
        return ordePhone;
    }

    public void setOrdePhone(String ordePhone) {
        this.ordePhone = ordePhone;
    }

    public String getOrdeProvince() {
        return ordeProvince;
    }

    public void setOrdeProvince(String ordeProvince) {
        this.ordeProvince = ordeProvince;
    }

    public String getOrdeStreet() {
        return ordeStreet;
    }

    public void setOrdeStreet(String ordeStreet) {
        this.ordeStreet = ordeStreet;
    }

    @Override
    public String toString() {
        return "BillBean{" +
                "ordeName='" + ordeName + '\'' +
                ", ordePhone='" + ordePhone + '\'' +
                ", ordeProvince='" + ordeProvince + '\'' +
                ", ordeStreet='" + ordeStreet + '\'' +
                '}';
    }
}
