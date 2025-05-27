# psychology

#### 介绍
民宿短租小程序

#### 软件架构
软件架构说明
uniapp  --微信小程序
api_code --系统后台数据、java代码
system_web  --后台管理界面


#### 安装教程

1.  运行后台管理系统：
    （1）打开：api_code\src\main\java\com\homestay\HomeStayApplication.java并且运行
    （2）终端 npm run dev
2.  小程序运行到H5:
    (1)cd ./uniapp/
    (2)yarn serve
3.  小程序运行到微信开发者工具:
    (1)cd ./uniapp/
    (2)yarn run dev:mp-weixin

#### 推荐算法
混合推荐算法文件：
1.\api_code\src\main\java\com\homestay\project\business\utils\RecommendUtil.java
2.\api_code\src\main\java\com\homestay\project\business\service\impl\RoomServiceImpl
##### 使用说明

1.  微信小程序配置文件：\api_code\src\main\java\com\homestay\common\utils\WeChatUtil.java

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 功能需求说明
基于推荐算法实现民宿短租小程序