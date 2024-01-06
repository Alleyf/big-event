import instance from "./request";


function get(url, params = null, success = defaultSuccess, error = defaultError) {
    return instance({
        method: 'get',
        url: url,
        params: params,
    }).then(res => success(res)).catch(err => error(err));
}

function post(url, params = null, data = null, success = defaultSuccess, error = defaultError) {
    return instance({
        method: 'post',
        url: url,
        params: params,
        data: data,
    }).then(res => success(res)).catch(err => error(err));
}

function put(url, params = null, data, success = defaultSuccess, error = defaultError) {
    return instance({
        method: 'put',
        url: url,
        params: params,
        data: data,
    }).then(res => success(res)).catch(err => error(err));
}

function patch(url, params = null, data = null, success = defaultSuccess, error = defaultError) {
    return instance({
        method: 'patch',
        url: url,
        params: params,
        data: data,
    }).then(res => success(res)).catch(err => error(err));
}


function del(url, params, success = defaultSuccess, error = defaultError) {
    return instance({
        method: 'delete',
        url: url,
        params: params,
    }).then(res => success(res)).catch(err => error(err));
}


function defaultSuccess(response) {
    console.log(response);
}


function defaultError(error) {
    console.log(error);
}

export default {
    get,
    post,
    put,
    patch,
    del
}