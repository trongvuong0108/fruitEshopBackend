package com.code.Service;

import com.code.Entity.token;

public interface tokenService {
    public token findByToken(String token);
    public void save(token signin_Token);
}
