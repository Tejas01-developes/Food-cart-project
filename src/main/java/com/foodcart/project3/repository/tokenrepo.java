package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.tokenschema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface tokenrepo extends JpaRepository<tokenschema,String> {
    Optional<tokenschema>findByemail(String email);
    Optional<tokenschema>deleteByemail(String email);
}
