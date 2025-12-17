package com.foodcart.project3.schemas;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "cart_iterms")
public class cartschema {
    @Id
  private String cartid=UUID.randomUUID().toString().substring(0,8);
  private String foodid;
  private String userid;

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Long totalprice) {
        this.totalprice = totalprice;
    }

    public Long getItermprice() {
        return itermprice;
    }

    public void setItermprice(Long itermprice) {
        this.itermprice = itermprice;
    }

    public String getItermname() {
        return itermname;
    }

    public void setItermname(String itermname) {
        this.itermname = itermname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    private String itermname;
  private Long itermprice;
  private Long totalprice;
  private Long quantity;
}
