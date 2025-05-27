package com.homestay.project.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.common.domain.City;
import com.homestay.project.common.domain.Province;
import org.apache.ibatis.annotations.Mapper;

/**
 * 城市Mapper接口
 * @date 2024-02-22
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

}
