package com.example.bigfamilyshopkeeper.controller;

import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;


import java.util.List;

public class CartHelper {
     Cart cart;
    private double RATE=0.05;

    public CartHelper(Cart cart){
        this.cart =cart;
    }

    public  void addGoodsToCart(GoodType good){
        List<GoodType> goodsAlreadyInCart=cart.getCartGoods();
        goodsAlreadyInCart.add(good);
        cart.setCartGoods(goodsAlreadyInCart);
    }
    public  void addNumberOfGoodsInCart(GoodType good){
        List<GoodType> goodsAlreadyInCart=cart.getCartGoods();

        for (GoodType goodType:goodsAlreadyInCart)
            if (goodType.getGoodTypeId().equals(good.getGoodTypeId()))
                goodType.setNumberInCart(good.getNumberInCart()+1);

        cart.setCartGoods(goodsAlreadyInCart);
    }

    public  void removeNumberOfGoodsInCart(GoodType good){
        List<GoodType> goodsAlreadyInCart=cart.getCartGoods();

        for (int index=0;index<goodsAlreadyInCart.size();index++)
            if (goodsAlreadyInCart.get(index).getGoodTypeId().equals(good.getGoodTypeId()))
                if (goodsAlreadyInCart.get(index).getNumberInCart()==1)
                    goodsAlreadyInCart.remove(index);
                    else
                    goodsAlreadyInCart.get(index).setNumberInCart(goodsAlreadyInCart.get(index).getNumberInCart()-1);

        cart.setCartGoods(goodsAlreadyInCart);
    }
    public  double calculateCartValue(){
        double price=0.00;
        List<GoodType> goodsInCart=cart.getCartGoods();
        for (GoodType goodType:goodsInCart){
            price+=goodType.getGoodRetailPrice()*goodType.getNumberInCart();
        }
        return price;
    }

    public double getServiceCharge(){
        return calculateCartValue()*RATE;
    }
    public double getTotalCharge(){
        return getServiceCharge()+calculateCartValue();
    }
}
