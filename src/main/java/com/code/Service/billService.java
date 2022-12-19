package com.code.Service;

import com.code.Entity.bill;

import java.util.List;

public interface billService {
    public void save(bill bill);
    public List<bill> getByUser(String username);
    public List<bill> getAll();
}
