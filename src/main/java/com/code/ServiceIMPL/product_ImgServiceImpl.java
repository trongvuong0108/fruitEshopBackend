package com.code.ServiceIMPL;

import com.code.Entity.productImg;
import com.code.Repository.product_ImgRepository;
import com.code.Service.product_ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class product_ImgServiceImpl implements product_ImgService {

    @Autowired
    private product_ImgRepository product_imgRepository ;
    @Override
    public void save(productImg product_img) {
        product_imgRepository.save(product_img);
    }
    @Override
    public List<productImg> getByProduct(String name) {
        List<productImg> product_imgs =  product_imgRepository.findAll();
        List<productImg> result = new ArrayList<productImg>();
        for (productImg img:product_imgs) {
            if(img.getProduct().getName().equals(name)) result.add(img);
        }
        return result;
    }

    @Override
    public void delete(int id) {
        product_imgRepository.deleteById(id);
    }


}
