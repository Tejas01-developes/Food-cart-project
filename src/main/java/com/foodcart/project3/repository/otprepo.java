package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.otpschema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface otprepo extends JpaRepository<otpschema,Long> {
    otpschema findByemail(String email);
    @Modifying
    @Transactional
    void deleteByemail(String email);
}
