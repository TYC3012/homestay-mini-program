package com.homestay.project.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.Hotel;

import java.util.List;

/**
 * 酒店信息Service接口
 * 
 * @author y
 * @date 2024-02-13
 */
public interface IHotelService extends IService<Hotel> {
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
     * 批量删除酒店信息
     * 
     * @param ids 需要删除的酒店信息主键集合
     * @return 结果
     */
    public int deleteHotelByIds(Long[] ids);

    /**
     * 删除酒店信息信息
     * 
     * @param id 酒店信息主键
     * @return 结果
     */
    public int deleteHotelById(Long id);

    /**
     * 查询酒店信息
     *
     * @param bossId 店主id
     * @return 酒店信息
     */
    public Hotel getHotelByBossId(Long bossId);
}
