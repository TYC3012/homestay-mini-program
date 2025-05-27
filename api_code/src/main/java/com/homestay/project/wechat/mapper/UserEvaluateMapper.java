package com.homestay.project.wechat.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.wechat.domain.UserEvaluate;

/**
 * 评价信息Mapper接口
 * 
 * @author 陶
 * @date 2024-04-06
 */
public interface UserEvaluateMapper extends BaseMapper<UserEvaluate> {
    /**
     * 查询评价信息
     * 
     * @param id 评价信息主键
     * @return 评价信息
     */
    public UserEvaluate selectUserEvaluateById(Long id);

    /**
     * 查询评价信息列表
     * 
     * @param userEvaluate 评价信息
     * @return 评价信息集合
     */
    public List<UserEvaluate> selectUserEvaluateList(UserEvaluate userEvaluate);

    /**
     * 新增评价信息
     * 
     * @param userEvaluate 评价信息
     * @return 结果
     */
    public int insertUserEvaluate(UserEvaluate userEvaluate);

    /**
     * 修改评价信息
     * 
     * @param userEvaluate 评价信息
     * @return 结果
     */
    public int updateUserEvaluate(UserEvaluate userEvaluate);

    /**
     * 删除评价信息
     * 
     * @param id 评价信息主键
     * @return 结果
     */
    public int deleteUserEvaluateById(Long id);

    /**
     * 批量删除评价信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserEvaluateByIds(Long[] ids);
}
