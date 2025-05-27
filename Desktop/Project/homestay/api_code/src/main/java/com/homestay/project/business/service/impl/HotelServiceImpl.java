package com.homestay.project.business.service.impl;

import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.utils.DateUtils;
import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.mapper.HotelMapper;
import com.homestay.project.business.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 酒店信息Service业务层处理
 * 
 * @author y
 * @date 2024-02-13
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper,Hotel> implements IHotelService {
    @Autowired
    private HotelMapper hotelMapper;

    /**
     * 查询酒店信息
     * 
     * @param id 酒店信息主键
     * @return 酒店信息
     */
    @Override
    public Hotel selectHotelById(Long id)
    {
        return hotelMapper.selectHotelById(id);
    }

    /**
     * 查询酒店信息列表
     * 
     * @param hotel 酒店信息
     * @return 酒店信息
     */
    @Override
    public List<Hotel> selectHotelList(Hotel hotel)
    {
        LambdaQueryWrapper<Hotel> lambdaQuery = Wrappers.lambdaQuery(Hotel.class);
        if(Objects.nonNull(hotel.getCreateBy())){
            lambdaQuery.eq(Hotel::getCreateBy,hotel.getCreateBy());
        }
        return hotelMapper.selectList(lambdaQuery);
    }

    /**
     * 新增酒店信息
     * 
     * @param hotel 酒店信息
     * @return 结果
     */
    @Override
    public int insertHotel(Hotel hotel)
    {
        hotel.setCreateTime(DateUtils.getNowDate());
        Long id = IdUtil.getSnowflake(10, 5).nextId();
        hotel.setId(id);
        return hotelMapper.insertHotel(hotel);
    }

    /**
     * 修改酒店信息
     * 
     * @param hotel 酒店信息
     * @return 结果
     */
    @Override
    public int updateHotel(Hotel hotel)
    {
        hotel.setUpdateTime(DateUtils.getNowDate());
        return hotelMapper.updateHotel(hotel);
    }

    /**
     * 批量删除酒店信息
     * 
     * @param ids 需要删除的酒店信息主键
     * @return 结果
     */
    @Override
    public int deleteHotelByIds(Long[] ids)
    {
        return hotelMapper.deleteHotelByIds(ids);
    }

    /**
     * 删除酒店信息信息
     * 
     * @param id 酒店信息主键
     * @return 结果
     */
    @Override
    public int deleteHotelById(Long id)
    {
        return hotelMapper.deleteHotelById(id);
    }

    @Override
    public Hotel getHotelByBossId(Long bossId) {
        LambdaQueryWrapper<Hotel> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Hotel::getBossId,bossId);
        lambdaQueryWrapper.eq(Hotel::getIsDelete,0);
        return hotelMapper.selectList(lambdaQueryWrapper).stream().findFirst().orElse(null);
    }
}
