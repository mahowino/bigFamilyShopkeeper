package com.example.bigfamilyshopkeeper.models;

import java.util.List;

public class Voucher {
    String voucherId;
    List<Goods> goods;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
