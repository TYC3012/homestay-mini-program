// let baseURL = 'https://apartment.cloudxmx.com'
let baseURL = 'http://192.168.0.103:8080'

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {
    return new Promise((resolve, reject) => {
        var access_token = uni.getStorageSync('access_token');
        console.log("post请求的token",access_token)
        uni.request({
            url: baseURL + url,
            data: params,
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${access_token}`,
            },
            method: "POST",
            success: (response) => {
                let res = response.data
                if (res.code === 200) {
                    resolve(res)
                } else {
                    reject(res)
                    showError(res);
                }
            },
            fail: (error) => {
                if (error && error.response) {
                    reject(error.response)
                    showError(error.response);
                }
            },
        });
    })
}

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
    var access_token = uni.getStorageSync('access_token');
    //console.log("get请求的token",access_token)
    return new Promise((resolve, reject) => {
        uni.request({
            url: baseURL + url,
            data: params,
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': `Bearer ${access_token}`,
            },
            method: "GET",
            success: (response) => {
                let res = response.data
                if (res.code==200) {
                    resolve(res)
                } else {
                    reject(res)
                    uni.showToast({
                        title: '服务器异常',
                        icon: 'none',
                        duration: 3000,
                        complete: function () {
                            setTimeout(function () {
                                uni.hideToast();
                            }, 3000);
                        }
                    });
                }
            },
            fail: (error) => {
                if (error && error.response) {
                    reject(error.response)
                    showError(error.response);
                }
            }
        });
    })
}

const showError = error => {
    let errorMsg = ''
    switch (error.code) {
        case 301:
            errorMsg = '未授权，请登录'
            break
        case 400:
            errorMsg = '请求参数错误'
            break
        case 403:
            errorMsg = '跨域拒绝访问'
            break
        case 404:
            errorMsg = '请求地址不存在'
            break
        case 500:
            errorMsg = '服务器内部错误'
            break
        case 504:
            errorMsg = '请求超时'
            break
        default:
            errorMsg = "请求失败"
            break
    }
    uni.showToast({
        title: errorMsg,
        icon: 'none',
        duration: 3000,
        complete: function () {
            setTimeout(function () {
                uni.hideToast();
            }, 3000);
        }
    });
}