package com.app.entity;

/**
 * @author SphinxXi
 * @classname ProductInfo
 * @createTime: 2023/12/17 19:35
 */
public class ProductInfo {
    private Integer productId;
    private String productName ="";
    private Integer productNum = -1;
    private double productPrice = -1.0;
    public ProductInfo(){
        super();
    }
    public ProductInfo(int id,String name,int num,double price){
        productId = id;
        productName = name;
        productNum = num;
        productPrice = price;
    }
    public ProductInfo(String name,int num,double price){
        productName = name;
        productNum = num;
        productPrice = price;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

