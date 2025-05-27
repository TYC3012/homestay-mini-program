package com.homestay.project.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.UserView;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户浏览信息，用户计算推荐Mapper接口
 * 
 * @author
 * @date 2024-02-29
 */
@Mapper
public interface UserViewMapper extends BaseMapper<UserView> {
    /**
     * 查询客户浏览信息，用户计算推荐
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 客户浏览信息，用户计算推荐
     */
    public UserView selectBusinessUserViewById(Long id);

    /**
     * 查询客户浏览信息，用户计算推荐列表
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 客户浏览信息，用户计算推荐集合
     */
    public List<UserView> selectBusinessUserViewList(UserView businessUserView);

    /**
     * 新增客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    public int insertBusinessUserView(UserView businessUserView);

    /**
     * 修改客户浏览信息，用户计算推荐
     * 
     * @param businessUserView 客户浏览信息，用户计算推荐
     * @return 结果
     */
    public int updateBusinessUserView(UserView businessUserView);

    /**
     * 删除客户浏览信息，用户计算推荐
     * 
     * @param id 客户浏览信息，用户计算推荐主键
     * @return 结果
     */
    public int deleteBusinessUserViewById(Long id);

    /**
     * 批量删除客户浏览信息，用户计算推荐
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusinessUserViewByIds(Long[] ids);
}
