package com.code.Model;

import com.code.Entity.category;
import lombok.Data;

@Data
public class productModel {
    private String name ;
    private float price_out;
    private category category;
    private String img;
    private float quantity;
}
