package com.code.Service;

import com.code.Entity.productImg;

import java.util.List;

public interface product_ImgService {
    public void save(productImg product_img);
    public List<productImg> getByProduct(String name);

    public void delete(int id);
}
