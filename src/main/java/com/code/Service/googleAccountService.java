package com.code.Service;

import com.code.Entity.googleAccount;

public interface googleAccountService {
    public void save(googleAccount googleAccount);
    public googleAccount get(String email);
    public void update(String email, String address, String phone);
}
