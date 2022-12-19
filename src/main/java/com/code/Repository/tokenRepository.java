package com.code.Repository;

import com.code.Entity.token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface tokenRepository extends JpaRepository<token,Integer> {
    public token findByToken(String token);

}
