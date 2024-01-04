import api from '@/utils/http'

const loginService = (params, success = null, error = null) => {
    return api.post('/api/user/login', params, null,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}

const registerService = (params, success = null, error = null) => {
    return api.post('/api/user/register', params, null,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}

export default {loginService, registerService}