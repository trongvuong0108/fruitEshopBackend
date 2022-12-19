package com.code.Controller;

import com.code.Entity.product;
import com.code.Entity.productImg;
import com.code.Model.Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.code.Service.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "product")
public class productControlller {
    @Autowired
    private productService productService;

    @Autowired
    private product_ImgService product_ImgService;

    @Autowired
    private categoryService categoryService;

    @GetMapping(value = "getAll")
    public List<product> getAll(){
        return productService.getAll();
    }

    @PostMapping(value = "save")
    public void save(
        @RequestParam("name") String name ,
        @RequestParam("quantity") String quantity ,
        @RequestParam("price_in") String price_in ,
        @RequestParam("price_out") String price_out,
        @RequestParam("pic") MultipartFile pic,
        @RequestParam("category") int category_id
    ){
        Uploader uploader = new Uploader();
        product product = new product();
        product.setDay_Create(LocalDateTime.now());
        product.setName(name);
        product.setQuantity(Float.parseFloat(quantity));
        product.setPrice_in(Float.parseFloat(price_in));
        product.setPrice_out(Float.parseFloat(price_out));
        product.setImg(uploader.uploadFile(pic));
        product.setCategory(categoryService.findById(category_id));
        productService.save(product);
        productImg product_img = new productImg();
        product_img.setImg(uploader.uploadFile(pic));
        product_img.setProduct(product);
        product_ImgService.save(product_img);
    }


    @PostMapping(value = "update")
    public void update(
            @RequestParam("id") int id ,
            @RequestParam("name") String name ,
            @RequestParam("quantity") String quantity ,
            @RequestParam("price_in") String price_in ,
            @RequestParam("price_out") String price_out,
            @RequestParam("pic") MultipartFile pic,
            @RequestParam("category") int category_id
    ){
        Uploader uploader = new Uploader();
        product product = productService.findById(id);
        product.setDay_Update(LocalDateTime.now());
        product.setName(name);
        product.setPrice_in(Float.parseFloat(price_in));
        product.setPrice_out(Float.parseFloat(price_out));
        product.setQuantity(Float.parseFloat(quantity));
        product.setCategory(categoryService.findById(category_id));
        product.setImg(uploader.uploadFile(pic));
        productService.save(product);
    }

    @GetMapping(value = "delete")
    public void update(@RequestParam int id){
//        List<product_Img> imgs= product_ImgService.getByProduct(productService.findById(id).getName());
//        for (product_Img img: imgs) {
//            product_ImgService.delete(img.getId());
//        }
//        productService.delete(id);
        product product = productService.findById(id);
        product.setEnable(false);
        productService.save(product);
    }

    @GetMapping(value = "addImg")
    public void addImg(
            @RequestParam("name") String name ,
            @RequestParam("pic") MultipartFile pic
            ){
        Uploader uploader = new Uploader();
        productImg product_img = new productImg();
        product_img.setProduct(productService.findByName(name));
        product_img.setImg(uploader.uploadFile(pic));
        product_ImgService.save(product_img);
    }
}
