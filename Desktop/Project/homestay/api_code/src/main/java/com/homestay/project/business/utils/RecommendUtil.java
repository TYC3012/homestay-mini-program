package com.homestay.project.business.utils;

import com.homestay.project.business.domain.Movie;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.User;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.vo.WeChatUserVo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 计算推荐算法工具类
 */
public class RecommendUtil {

    /**
     * 在给定username的情况下，计算其他用户和它的距离并排序
     * @param userId
     * @return
     */
    private static Map<Double, Long> computeNearestNeighbor(Long userId, List<WeChatUserVo> users) {
        Map<Double, Long> distances = new TreeMap<>();

        //取出当前登录用户的用户信息
        Map<Long,WeChatUserVo> userMap=users.stream().collect(Collectors.toMap(WeChatUserVo::getId, g -> g));;
        WeChatUserVo u1 = userMap.getOrDefault(userId,new WeChatUserVo());

        //剩余的微信用户信息计算
        for (int i = 0; i < users.size(); i++) {
            WeChatUserVo u2 = users.get(i);
            if (!u2.getId().equals(userId)) {
                System.out.println("u2.getId()::"+u2.getId());
                double distance = pearson_dis(u2.getRoomList(), u1.getRoomList());
                System.out.println("distance::"+distance);
                distances.put(distance, u2.getId());
            }
        }
        System.out.println("该用户与其他用户的皮尔森相关系数 -> " + distances);
        return distances;
    }

    /**
     * 计算2个打分序列间的pearson距离
     * 选择公式四进行计算 List<Room>
     * @param rating1
     * @param rating2
     * @return
     */
    private static double pearson_dis(List<RoomVO> rating1, List<RoomVO> rating2) {
        int n=rating1.size();

        List<Integer> rating1ScoreCollect = rating1.stream().map(A -> A.getTimeNumber()).collect(Collectors.toList());
        List<Integer> rating2ScoreCollect = rating2.stream().map(A -> A.getTimeNumber()).collect(Collectors.toList());

        double Ex= rating1ScoreCollect.stream().mapToDouble(x->x).sum();
        double Ey= rating2ScoreCollect.stream().mapToDouble(y->y).sum();
        double Ex2=rating1ScoreCollect.stream().mapToDouble(x->Math.pow(x,2)).sum();
        double Ey2=rating2ScoreCollect.stream().mapToDouble(y->Math.pow(y,2)).sum();
        double Exy= IntStream.range(0,n).mapToDouble(i->rating1ScoreCollect.get(i)*rating2ScoreCollect.get(i)).sum();
        double numerator=Exy-Ex*Ey/n;
        double denominator=Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));
        if (denominator==0) return 0.0;
        return numerator/denominator;
    }

    public static List<RoomVO> recommend(Long userId, List<WeChatUserVo> users) {
        //找到最近邻
        Map<Double, Long> distances = computeNearestNeighbor(userId, users);
        Long nearestId = distances.values().iterator().next();
        System.out.println("最近邻的用户id -> " + nearestId);

        //找到最近邻浏览过，但是我们没浏览过的房间，计算推荐
        WeChatUserVo neighborRatings = new WeChatUserVo();
        Map<Long,WeChatUserVo> userMap=users.stream().collect(Collectors.toMap(WeChatUserVo::getId, g -> g));;
        neighborRatings=userMap.getOrDefault(nearestId,new WeChatUserVo());
        System.out.println("最近邻浏览过的房间信息 -> " + neighborRatings.getRoomList());

        //用户自己看过的房间
        WeChatUserVo userRatings = userMap.getOrDefault(userId,new WeChatUserVo());;
        System.out.println("用户浏览过的房间信息 -> " + userRatings.getRoomList());

        //根据自己和邻居浏览过的房间计算推荐的房间
        List<RoomVO> recommendationMovies = new ArrayList<>();
        for (RoomVO roomVO : neighborRatings.getRoomList()) {
            if (userRatings.find(roomVO.getId()) == null) {
                System.out.println("=======计算时的房间信息：："+roomVO);
                recommendationMovies.add(roomVO);
            }else{
                recommendationMovies.add(userRatings.find(roomVO.getId()));
            }
        }
        //recommendationMovies= (List<RoomVO>) recommendationMovies.stream().sorted();
        return recommendationMovies;
    }

}
