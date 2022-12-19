package com.code.Model;

import lombok.Data;

import java.util.List;

@Data
public class billModel {
    List<detail_BillModel> detailList;
    String name;
    String address;
    String phone;
}
