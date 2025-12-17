package com.foodcart.project3.schemas;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="Addfood")
public class addproductschema {
    @Column(name = "image_url", nullable = false)
    private String imageurl;

    public Long getFoodid() {
        return foodid;
    }

    public void setFoodid(Long foodid) {
        this.foodid = foodid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
   @Column(name = "Food_id", nullable = false)
    private Long foodid;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Price", nullable = false)
    private Double price;
    @Column(name = "Category", nullable = false)
    private String category;
    @Column(name = "description")
    private String description;

}
