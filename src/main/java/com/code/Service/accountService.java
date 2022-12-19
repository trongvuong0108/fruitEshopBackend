package com.code.Service;

import com.code.Entity.account;

import java.util.List;

public interface accountService {
    public void save(account account);
    public account getByUserName(String username);

    public List<account> getAll();
}
