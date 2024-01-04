// 定制请求的实例

// 导入axios
import axios from 'axios'
import {ElNotification} from 'element-plus'
import {useTokenStore} from "@/stores/token.js";
import router from "@/router";

// 定义请求公共前缀,超时时间，跨域，请求头和实例
const baseURL = '';
const instance = axios.create({
    baseURL: baseURL,
    timeout: 5000,
})

// 拦截器是异步的，所以需要返回一个promise
// 添加响应拦截器
instance.interceptors.response.use(
    response => {
        // 对响应数据做些事
        // console.log(response.data)
        // 异步操作的状态转换为成功返回
        if (response.data.code === 200) {
            return response.data // 直接返回包装后的数据，不含响应头等附加信息。
        } else {
            // 异步操作的状态转换为失败返回
            ElNotification.error(response.data.message ? response.data.message : '服务异常');
            return Promise.reject(response.data);
        }
    },
    error => {
        // 判断状态码
        const errorMessages = {
            401: '未登录',
            403: '无权限',
            404: '资源不存在',
            500: '服务器内部异常',
        };
        // 根据状态码，返回对应的错误信息
        const errorMessage = errorMessages[error.response.status] || '未知错误';
        ElNotification.error(errorMessage);
        if (error.response.status) {
            router.push({path: '/login'})
        }
        return Promise.reject(error);
    }
)

// 添加请求拦截器
instance.interceptors.request.use(
    config => {
        // 在发送请求之前做些什么
        let tokenStore = useTokenStore();
        // console.log("token：" + tokenStore.token)
        if ((config.url !== '/user/login' || config.url !== '/user/register') && tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        if (config.method === 'post') {
            config.headers['Content-Type'] = 'application/json'
        }
        return config
    },
    error => {
        // 对请求错误做些什么
        ElNotification.error(error.message ? error.message : '请求异常');
        Promise.reject(error)
    }
)

export default instance