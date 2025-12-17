package com.foodcart.project3.repository;

import com.foodcart.project3.schemas.userschema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface userrepo extends JpaRepository<userschema,String> {
Optional<userschema>findByemail(String email);
}
