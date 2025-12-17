package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.addproductschema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface foodrepo extends JpaRepository<addproductschema, String> {
    List<addproductschema>findByCategoryIgnoreCase(String category);
}
