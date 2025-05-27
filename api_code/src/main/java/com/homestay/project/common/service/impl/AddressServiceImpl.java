package com.homestay.project.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.homestay.project.common.domain.Province;
import com.homestay.project.common.mapper.ProvinceMapper;
import com.homestay.project.common.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public List<Province> provinceList() {
        LambdaQueryWrapper<Province> lambdaQuery = Wrappers.lambdaQuery(Province.class);

        return provinceMapper.selectList(lambdaQuery);
    }
}
