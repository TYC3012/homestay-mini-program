package com.homestay.project.business.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.project.business.domain.*;
import com.homestay.project.business.mapper.*;
import com.homestay.project.business.service.IRoomService;
import com.homestay.project.business.utils.RecommendUtil;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.wechat.mapper.WechatUserMapper;
import com.homestay.project.wechat.vo.WeChatUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


/**
 * 房间信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private UserViewMapper userViewMapper;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private UserCollectMapper userCollectMapper;
    @Autowired
    private OrderMapper orderMapper;




    @Override
    public Room selectRoomById(Long id) {
        Room room=roomMapper.selectById(id);
        //记录用户的浏览信息

        return room;
    }

    @Override
    public List<RoomVO> selectRoomList(RoomVO room) {
        return roomMapper.getRoomList(room);
    }

    @Override
    public int insertRoom(RoomVO roomVO) {
        Long id = IdUtil.getSnowflake(10, 5).nextId();
        roomVO.setId(id);
        return roomMapper.insert(roomVO);
    }

    @Override
    public int updateRoom(Room room) {
        /*if(roomVO.getDetailsImgList().size()>0){
            List<RoomDetailsImg> detailsImgList=roomVO.getDetailsImgList();
            detailsImgList.stream().forEach(detail->{
                detail.setRoomId(roomVO.getId());
                roomDetailsImgMapper.insert(detail);
            });

        }*/
        return roomMapper.updateById(room);
    }

    @Override
    public int deleteRoomByIds(Long[] ids) {
        return roomMapper.deleteRoomByIds(ids);
    }

    @Override
    public int deleteRoomById(Long id) {
        return roomMapper.deleteById(id);
    }

    /**
     * 默认推荐10个房间信息
     * 混合推荐算法，
     * 1，优先推荐用户下过单的民宿信息 2个
     * 2，使用基于协同过滤算法推荐房间信息3个
     * 3，最后推荐浏览人数最多的房间5个
     * @param roomVO
     * @return
     */
    @Override
    public List<RoomVO> recommend(RoomVO roomVO) {
        Long userId=roomVO.getUserId();

        //先将所有的用户信息查询回来
        List<WeChatUserVo> users= BeanUtil.copyToList(wechatUserMapper.selectList(new QueryWrapper<>()),WeChatUserVo.class);

        //查询出用户的浏览信息
        List<RoomVO> viewList=roomMapper.getViewRoomList(roomVO);
        Map<Long, List<RoomVO>> groupedItems = viewList.stream().collect(Collectors.groupingBy(RoomVO::getUserId));
        List<RoomVO> otherList=new ArrayList<>();

        List<WeChatUserVo> newUserList=new ArrayList<>();
        users.stream().forEach(user->{
            List<RoomVO> all=groupedItems.getOrDefault(user.getId(),new ArrayList<>());
            if(!roomVO.getUserId().equals(user.getId())){
                List<RoomVO> other=groupedItems.getOrDefault(user.getId(),new ArrayList<>());
                if(other.size()>0){
                    //没有另外的用户
                    otherList.addAll(other);
                }
            }
            if(all.size()>0){
                user.setRoomList(all);
                newUserList.add(user);
            }
        });

        //设置返回的房间集合
        Set<RoomVO> roomVOList=new HashSet<>();
        //首先查询出用户下过单次数最多的2个房间 第一种推荐
        List<RoomVO>  orderList= orderMapper.userOrderList(userId);
        roomVOList.addAll(orderList.stream().limit(2).collect(Collectors.toList()));
        //使用基于协同过滤算法推荐房间信息5个 第二种推荐
        List<RoomVO>  pearsonList= RecommendUtil.recommend(roomVO.getUserId(),newUserList);
        roomVOList.addAll(pearsonList.stream().limit(5).collect(Collectors.toList()));
        //最后推荐浏览人数最多的房间3个 第三种推荐
        List<RoomVO> top3ViewList=viewList.stream().sorted(Comparator.comparing(RoomVO::getTimeNumber).reversed()).limit(3).collect(Collectors.toList());
        roomVOList.addAll(top3ViewList);
        //如果这三种算法推荐不满10个，补全房间信息为10个

        Set<Long> ids=roomVOList.stream().distinct().map(RoomVO::getId).collect(Collectors.toSet());
        Integer surplus=10-ids.size();
        if(surplus>0){
            //如果没有多个以上用户，直接返回房间列表
            LambdaQueryWrapper<Room> roomWrapper=new LambdaQueryWrapper<>();
            roomWrapper.eq(Room::getIsDelete,0);
            List<RoomVO> allList=BeanUtil.copyToList(roomMapper.selectList(roomWrapper),RoomVO.class);
            //roomVOList.addAll(allList.stream().filter(room ->!ids.contains(room.getId())).limit(surplus).collect(Collectors.toList()));
            for (RoomVO vo : allList) {
               if(!ids.contains(vo.getId())){
                   //System.out.println("不包含：："+vo.getId());
                   ids.add(vo.getId());
               }
            }
        }
//        System.out.println("idsidsidsids：："+ids.size());
        //最后将这10个房间信息过滤掉重复的返回给前端
//        Set<Long> roomIds=roomVOList.stream().distinct().map(RoomVO::getId).collect(Collectors.toSet());
        LambdaQueryWrapper<Room> allWrapper=new LambdaQueryWrapper<>();
        allWrapper.eq(Room::getIsDelete,0);
        allWrapper.in(Room::getId,ids);
        List<RoomVO> endList=BeanUtil.copyToList(roomMapper.selectList(allWrapper),RoomVO.class);

        return endList;
    }






}
