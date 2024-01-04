import api from '@/utils/http'

const loginService = (params, success, error) => {
    return api.post('/api/user/login', params, null,
        res => success(res),
        err => error(err))
}

const registerService = (params, success, error) => {
    return api.post('/api/user/register', params, null,
        res => success(res),
        err => error(err))
}

export default {loginService, registerService}