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

const userInfoService = (success = null, error = null) => {
    return api.get('/api/user/userInfo', null,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}

const updateInfoService = (data, success = null, error = null) => {
    return api.put('/api/user/update', null, data,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}

const updatePasswordService = (data, success = null, error = null) => {
    return api.patch(`/api/user/updatePassword`, null, data,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}

const updateAvatarService = (params, success = null, error = null) => {
    return api.patch(`/api/user/updateAvatar/${params.id}`, params, null,
        res => success ? success(res) : res,
        err => error ? error(err) : err)
}


export default {
    loginService,
    registerService,
    userInfoService,
    updateInfoService,
    updatePasswordService,
    updateAvatarService
}