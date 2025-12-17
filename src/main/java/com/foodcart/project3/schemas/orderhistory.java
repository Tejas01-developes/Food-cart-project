package com.foodcart.project3.schemas;

import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orderhistory")
public class orderhistory {
    @Id
 private String orderhistoryid=UUID.randomUUID().toString().substring(0,8);
 private String foodid;
 private String itermname;
 private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderhistoryid() {
        return orderhistoryid;
    }

    public void setOrderhistoryid(String orderhistoryid) {
        this.orderhistoryid = orderhistoryid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getItermname() {
        return itermname;
    }

    public void setItermname(String itermname) {
        this.itermname = itermname;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public LocalDateTime getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(LocalDateTime ordertime) {
        this.ordertime = ordertime;
    }

    private Long quantity;
 private double totalprice;
 private LocalDateTime ordertime=LocalDateTime.now();
}
