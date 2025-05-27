package com.homestay.project.business.domain;

import com.homestay.project.business.domain.Movie;
import com.homestay.project.business.domain.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Recommend {

    /**
     * 在给定username的情况下，计算其他用户和它的距离并排序
     * @param username
     * @return
     */
    private Map<Double, String> computeNearestNeighbor(String username, List<User> users) {
        Map<Double, String> distances = new TreeMap<>();

        User u1 = new User(username);
        for (User user:users) {
            if (username.equals(user.username)) {
                u1 = user;
            }
        }

        for (int i = 0; i < users.size(); i++) {
            User u2 = users.get(i);

            if (!u2.username.equals(username)) {
                double distance = pearson_dis(u2.movieList, u1.movieList);
                distances.put(distance, u2.username);
            }

        }
        System.out.println("该用户与其他用户的皮尔森相关系数 -> " + distances);
        return distances;
    }


    /**
     * 计算2个打分序列间的pearson距离
     * 选择公式四进行计算
     * @param rating1
     * @param rating2
     * @return
     */
    private double pearson_dis(List<Movie> rating1, List<Movie> rating2) {
        int n=rating1.size();
        List<Integer> rating1ScoreCollect = rating1.stream().map(A -> A.score).collect(Collectors.toList());
        List<Integer> rating2ScoreCollect = rating2.stream().map(A -> A.score).collect(Collectors.toList());

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


    public List<Movie> recommend(String username, List<User> users) {
        //找到最近邻
        Map<Double, String> distances = computeNearestNeighbor(username, users);
        String nearest = distances.values().iterator().next();
        System.out.println("最近邻 -> " + nearest);

        //找到最近邻看过，但是我们没看过的电影，计算推荐
        User neighborRatings = new User();
        for (User user:users) {
            if (nearest.equals(user.username)) {
                neighborRatings = user;
            }
        }
        System.out.println("最近邻看过的电影 -> " + neighborRatings.movieList);

        User userRatings = new User();
        for (User user:users) {
            if (username.equals(user.username)) {
                userRatings = user;
            }
        }
        System.out.println("用户看过的电影 -> " + userRatings.movieList);

        //根据自己和邻居的电影计算推荐的电影
        List<Movie> recommendationMovies = new ArrayList<>();
        for (Movie movie : neighborRatings.movieList) {
            if (userRatings.find(movie.movieName) == null) {
                recommendationMovies.add(movie);
            }
        }
        Collections.sort(recommendationMovies);
        return recommendationMovies;
    }


    public static void main(String[] args) {
        //输入用户总量
        List<User> users = new ArrayList<>();
        users.add(new User("小明")
                .set("中国合伙人", 50)
                .set("太平轮", 30)
                .set("荒野猎人", 45)
                .set("老炮儿", 50)
                .set("我的少女时代", 30)
                .set("肖洛特烦恼", 45)
                .set("火星救援", 50));

        users.add(new User("小红")
                .set("小时代4", 40)
                .set("荒野猎人", 30)
                .set("我的少女时代", 50)
                .set("肖洛特烦恼", 50)
                .set("火星救援", 30)
                .set("后会无期", 30));


        users.add(new User("小阳")
                .set("小时代4", 20)
                .set("中国合伙人", 50)
                .set("我的少女时代", 30)
                .set("老炮儿", 50)
                .set("肖洛特烦恼", 45)
                .set("速度与激情7", 50));

        users.add(new User("小四")
                .set("小时代4", 50)
                .set("中国合伙人", 30)
                .set("我的少女时代", 40)
                .set("匆匆那年", 40)
                .set("速度与激情7", 35)
                .set("火星救援", 35)
                .set("后会无期", 45));

        users.add(new User("六爷")
                .set("小时代4", 20)
                .set("中国合伙人", 40)
                .set("荒野猎人", 45)
                .set("老炮儿", 50)
                .set("我的少女时代", 20));

        users.add(new User("小李")
                .set("荒野猎人", 50)
                .set("盗梦空间", 50)
                .set("我的少女时代", 30)
                .set("速度与激情7", 50)
                .set("蚁人", 45)
                .set("老炮儿", 40)
                .set("后会无期", 35));

        users.add(new User("隔壁老王")
                .set("荒野猎人", 50)
                .set("中国合伙人", 40)
                .set("我的少女时代", 10)
                .set("Phoenix", 50)
                .set("甄嬛传", 40)
                .set("The Strokes", 50));

        users.add(new User("邻村小芳")
                .set("小时代4", 40)
                .set("我的少女时代", 45)
                .set("匆匆那年", 45)
                .set("甄嬛传", 25)
                .set("The Strokes", 30));


        Recommend recommend = new Recommend();
        List<Movie> recommendationMovies = recommend.recommend("小明", users);
        System.out.println("-----------------------");
        System.out.println("推荐结果如下：");
        for (Movie movie : recommendationMovies) {
            System.out.println("电影："+movie.movieName+" ,评分："+movie.score);
        }
    }

}
