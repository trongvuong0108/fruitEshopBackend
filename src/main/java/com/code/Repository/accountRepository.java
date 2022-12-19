package com.code.Repository;

import com.code.Entity.account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface accountRepository extends JpaRepository<account,Integer> {
    public account findByUsername(String username);
}
