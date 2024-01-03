import api from '@/util/http/'


// 获取所有文章
function articleGetAll (success, error) {
  return api.get("/api/article/list", null, res => success(res), err => error(err));
};

// 获取单个文章
function articleGetByPage (params, success, error) {
  return api.get(`/api/article/page`, params, res => success(res), err => error(err));
};


export default {
  articleGetAll,
  articleGetByPage
};