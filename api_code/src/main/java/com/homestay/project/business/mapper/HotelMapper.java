package com.homestay.project.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Homestay;
import com.homestay.project.business.domain.Hotel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 酒店信息Mapper接口
 * 
 * @author y
 * @date 2024-02-13
 */
@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {
    /**
     * 查询酒店信息
     * 
     * @param id 酒店信息主键
     * @return 酒店信息
     */
    public Hotel selectHotelById(Long id);

    /**
     * 查询酒店信息列表
     * 
     * @param hotel 酒店信息
     * @return 酒店信息集合
     */
    public List<Hotel> selectHotelList(Hotel hotel);

    /**
     * 新增酒店信息
     * 
     * @param hotel 酒店信息
     * @return 结果
     */
    public int insertHotel(Hotel hotel);

    /**
     * 修改酒店信息
     * 
     * @param hotel 酒店信息
     * @return 结果
     */
    public int updateHotel(Hotel hotel);

    /**
     * 删除酒店信息
     * 
     * @param id 酒店信息主键
     * @return 结果
     */
    public int deleteHotelById(Long id);

    /**
     * 批量删除酒店信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHotelByIds(Long[] ids);
}
