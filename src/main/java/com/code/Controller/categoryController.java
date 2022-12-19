package com.code.Controller;

import com.code.Entity.category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.code.Service.*;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class categoryController {
    @Autowired
    private categoryService categoryService;

    @GetMapping(value = "getAll")
    private List<category> getAll(){
        return categoryService.getAll();
    }


}
