package com.zhao.bill.designmodel.factorymodel;

/**
 * 客户类
 * Created by Bill on 2017/12/23.
 */
public class Client {

    public static void main(String[] args) {

        ConcreateFactory factory = new ConcreateFactory();
        Product product = factory.createProduct();
        product.method();

    }
}
