package com.homestay.project.common.controller;

import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.common.domain.Province;
import com.homestay.project.common.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.homestay.framework.web.domain.AjaxResult.success;

/**
 * 行政区划信息，省市区联动
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 查询省份列表
     */
    @GetMapping("/provinceList")
    public AjaxResult list()
    {
        List<Province> list = addressService.provinceList();
        return success(list);
    }

}
