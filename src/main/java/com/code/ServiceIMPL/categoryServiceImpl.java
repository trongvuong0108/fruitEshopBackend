package com.code.ServiceIMPL;

import com.code.Entity.category;
import com.code.Service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryServiceImpl implements categoryService {
    @Autowired
    private com.code.Repository.categoryRepository categoryRepository;
    @Override
    public List<category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public category findById(int id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void save(category category) {
        categoryRepository.save(category);
    }
}
