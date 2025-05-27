import QQMapWX from './qqmap-wx-jssdk.min.js' //引入下载的js

const qqmapsdk = new QQMapWX({
    key: 'UHVBZ-YPL6S-CAXOU-65HLI-SZ2PE-GOFIQ',  //之前在腾讯位置服务申请的key
})

// 腾讯地图经纬度转中文地址
export function getAddress() {
    return new Promise((resove, reject) => {
        wx.getLocation({
            type: 'gcj02',
            success(res) {
                qqmapsdk.reverseGeocoder({
                    location: {
                        latitude: res.latitude,
                        longitude: res.longitude,
                    },
                    success: (res) => {
                        resove(res)
                    },
                    fail: (e) => {
                        reject(e)
                        uni.showToast({
                          title: '定位失败',
                          duration: 2000,
                          icon: 'none',
                        })
                    },
                })
            }
        })
    })
}

// 计算两个位置的距离
export function calculatedDistance(long, lat, form) {
    return new Promise((resove, reject) => {
        //调用距离计算接口
        qqmapsdk.calculateDistance({
            mode: 'walking',//可选值：'driving'（驾车）、'walking'（步行），不填默认：'walking',可不填
            //from参数不填默认当前地址
            //获取表单提交的经纬度并设置from和to参数（示例为string格式）
            from: form, //若起点有数据则采用起点坐标，若为空默认当前地址
            to: lat + "," + long, //终点坐标
            success: function (res) {//成功后的回调
                var res = res.result;
                var dis = [];
                for (var i = 0; i < res.elements.length; i++) {
                    dis.push(res.elements[i].distance); //将返回数据存入dis数组，
                }
                resove(dis)
            },
            fail: function (error) {
                console.error(error);
                reject(error)
            },
            complete: function (res) {
            }
        });
    })
}