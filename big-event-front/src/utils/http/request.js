// 定制请求的实例

// 导入axios
import axios from 'axios'
import {ElNotification} from 'element-plus'


// 定义请求公共前缀,超时时间，跨域，请求头和实例
const baseURL = '';
const instance = axios.create({
    baseURL: baseURL,
    timeout: 5000,
    crossDomain: true,
    headers: {
        'Authorization': localStorage.getItem('token'),
    }
})

// 拦截器是异步的，所以需要返回一个promise
// 添加响应拦截器
instance.interceptors.response.use(
    response => {
        // 对响应数据做些事
        return response.data // 直接返回包装后的数据，不含响应头等附加信息。
    },
    error => {
        // 对响应错误做些事，异步的状态转化成失败的状态
        ElNotification({
            title: '请求错误',
            message: error.message,
            type: 'error',
            duration: 3000
        })
        return Promise.reject(error)
    }
)

// 添加请求拦截器
instance.interceptors.request.use(
    config => {
        if (config.method === 'post') {
            config.headers['Content-Type'] = 'application/json'
        }
        // 在发送请求之前做些什么
        return config
    },
    error => {
        // 对请求错误做些什么
        ElNotification({
            title: '请求错误',
            message: error.message,
            type: 'error',
            duration: 3000
        })
        return Promise.reject(error)
    }
)

export default instance