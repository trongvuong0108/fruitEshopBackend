package com.code.Service;

import com.code.Entity.category;

import java.util.List;

public interface categoryService {
    public List<category> getAll();
    public category findById(int id);
    public void save(category category);
}
