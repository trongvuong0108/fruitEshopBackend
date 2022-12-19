package com.code.Repository;

import com.code.Entity.googleAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface googleAccountRepository extends JpaRepository<googleAccount,Integer> {
    googleAccount findByEmail(String email);
}
