import api from '@/utils/http'

function login (params) {
  console.log(params);
  // alert(JSON.stringify(params))
  if (!params.username || !params.password) {
    return Promise.reject(new Error('用户名或密码不能为空'))
  }
  return api.post('/api/user/login', params, null,
    res => {
      localStorage.setItem('token', res.data.data)
      console.log(res.data);
    },
    err => {
      console.log(err)
    })
}


export default { login }