package com.example.bigfamilyshopkeeper.controller;

import com.example.bigfamilyshopkeeper.models.Cart;
import com.example.bigfamilyshopkeeper.models.GoodType;


import java.math.BigDecimal;
import java.util.List;

public class CartHelper {
     Cart cart;
    private double RATE=0.05,priceToTopUp;
    int wholesalePacksToBeBrought,goodsToAdd;

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
    private void calculateValues(){
        //get the modulus of the
        priceToTopUp=0.00;
        wholesalePacksToBeBrought=0;
        goodsToAdd=0;

        List<GoodType> goodsInCart=cart.getCartGoods();
        wholesalePacksToBeBrought+=goodsInCart.size();

        for (GoodType goodType:goodsInCart){
            BigDecimal dividend = new BigDecimal(goodType.getNumberInCart());
            BigDecimal divisor = new BigDecimal(goodType.getWholesaleQuantities());

            BigDecimal[] quotientAndRemainder = dividend.divideAndRemainder(divisor);
            BigDecimal quotient = quotientAndRemainder[0];
            BigDecimal remainder = quotientAndRemainder[1];
            wholesalePacksToBeBrought+=quotient.intValue();
            int wholesalePacks=(goodType.getWholesaleQuantities()-remainder.intValue());
            goodsToAdd+=wholesalePacks;
            priceToTopUp+=wholesalePacks*goodType.getGoodWholesalePrice();
        }

    }
    public  double getGoodsToAdd(){
        calculateValues();
        return goodsToAdd;
    }

    public double getWholesaleGoodsToBeBrought(){
        return wholesalePacksToBeBrought;
    }
    public double getTotalCharge(){
        calculateValues();
        return priceToTopUp;
    }

    public int getBulksForSpecificProduct(GoodType good) {
        BigDecimal dividend = new BigDecimal(good.getNumberInCart());
        BigDecimal divisor = new BigDecimal(good.getWholesaleQuantities());

        BigDecimal[] quotientAndRemainder = dividend.divideAndRemainder(divisor);
        BigDecimal quotient = quotientAndRemainder[0];

        return quotient.intValue()+1;
    }
    public double getRemainingAmountForNextBulk(GoodType good) {
        BigDecimal dividend = new BigDecimal(good.getNumberInCart());
        BigDecimal divisor = new BigDecimal(good.getWholesaleQuantities());

        BigDecimal[] quotientAndRemainder = dividend.divideAndRemainder(divisor);
        BigDecimal quotient = quotientAndRemainder[0];
        BigDecimal remainder = quotientAndRemainder[1];
        wholesalePacksToBeBrought+=quotient.intValue();

        return (good.getWholesaleQuantities()-remainder.intValue())+1;
    }
}
