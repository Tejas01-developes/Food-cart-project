package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.cartschema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface cartrepo extends JpaRepository<cartschema,String> {
   cartschema findByFoodidAndUserid(String foodid,String userid);
    cartschema deleteByFoodidAndUserid(String foodid,String userid);
    List<cartschema> findByUserid(String userid);
    @Transactional
//    @Query("DELETE FROM carts c where c.userid = :userid")
    List<cartschema> deleteByUserid(String userid);

}
