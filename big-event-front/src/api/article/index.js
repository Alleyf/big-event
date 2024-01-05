import api from '@/utils/http'


const categoryGetAll = (success = null, error = null) => {
    return api.get("/api/category/list", null, res => success ? success(res) : res, err => error ? error(err) : err);
}

const categoryUpdate = (data, success = null, error = null) => {
    return api.put(`/api/category/update`, null, data, res => success ? success(res) : res, err => error ? error(err) : err);
}

const categoryAdd = (data, success = null, error = null) => {
    return api.post(`/api/category/add`, null, data, res => success ? success(res) : res, err => error ? error(err) : err);
}

const categoryDelete = (params, success = null, error = null) => {
    return api.del(`/api/category/delete`, params, res => success ? success(res) : res, err => error ? error(err) : err);
}

// 获取所有文章
const articleGetAll = (success = null, error = null) => {
    return api.get("/api/article/list", null, res => success ? success(res) : res, err => error ? error(err) : err);
}

// 获取单个文章
const articleGetByPage = (params, success = null, error = null) => {
    return api.get(`/api/article/page`, params, res => success ? success(res) : res, err => error ? error(err) : err);
}

const articleUpdate = (data, success = null, error = null) => {
    return api.put(`/api/article/update`, null, data, res => success ? success(res) : res, err => error ? error(err) : err);
}

const articleAdd = (data, success = null, error = null) => {
    return api.post(`/api/article/add`, null, data, res => success ? success(res) : res, err => error ? error(err) : err);
}


export default {
    categoryGetAll,
    categoryUpdate,
    categoryAdd,
    categoryDelete,
    articleGetAll,
    articleGetByPage,
    articleAdd,
    articleUpdate
};