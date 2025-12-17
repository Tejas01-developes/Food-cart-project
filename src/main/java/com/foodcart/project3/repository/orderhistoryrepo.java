package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.orderhistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderhistoryrepo extends JpaRepository<orderhistory,String> {
}
