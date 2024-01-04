<script setup>
import {Lock, User} from '@element-plus/icons-vue'
import {reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import user from '@/api/user'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false);
// 定义数据模型
//用于注册的数据模型
const form = ref()
const registerData = ref({
  username: '',
  password: '',
  rePassword: ''
});
//自定义确认密码的校验函数
const rePasswordValid = (rule, value, callback) => {
  if (value == null || value === '') {
    return callback(new Error('请再次确认密码'))
  }
  if (registerData.value.password !== value) {
    return callback(new Error('两次输入密码不一致'))
  }
  callback()
};
//用于注册的表单校验模型
const registerDataRules = reactive({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 4, max: 16, message: '用户名的长度必须为4~16位', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 4, max: 16, message: '密码长度必须为4~16位', trigger: 'blur'}
  ],
  rePassword: [
    {validator: rePasswordValid, trigger: 'blur'}
  ]
});

// 调用后端接口
const register = () => {
  // 表单校验
  form.value.validate((valid) => {
    if (valid) {
      user.registerService(registerData.value, res => {
        if (res.code === 200) {
          ElMessage.success('注册成功')
        } else {
          ElMessage.error('注册失败:' + res.message)
        }
      }, err => {
        console.log(err)
        ElMessage.error('系统内部发生错误')
      })
    }
    return false
  })
};

//登录数据模型
const login = () => {
  // 表单校验
  console.log(form.value);
  form.value.validate((valid) => {
    if (valid) {
      user.loginService(registerData.value, res => {
            if (res.code === 200) {
              ElMessage.success('登录成功')
              localStorage.setItem('token', res.data)
              console.log(res);
            } else {
              ElMessage.error('登录失败:' + res.message)
            }
          },
          err => {
            console.log(err)
          })
    } else {
      return false
    }
  })
}


const switchForm = () => {
  isRegister.value = !isRegister.value
  form.value.resetFields()
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :offset="3" :span="6" class="form">
      <!-- 注册表单 -->
      <el-form v-if="isRegister" ref="form" :model="registerData" :rules="registerDataRules" autocomplete="off"
               size="large">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input v-model="registerData.username" :prefix-icon="User" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerData.password" :prefix-icon="Lock" placeholder="请输入密码"
                    type="password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input v-model="registerData.rePassword" :prefix-icon="Lock" placeholder="请输入再次密码"
                    type="password"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button auto-insert-space class="button" type="primary" @click="register">
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link :underline="false" type="info" @click="switchForm">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form v-else ref="form" :model="registerData" :rules="registerDataRules" autocomplete="off" size="large">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input v-model="registerData.username" :prefix-icon="User" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerData.password" :prefix-icon="Lock" name="password" placeholder="请输入密码"
                    type="password"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link :underline="false" type="primary">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button auto-insert-space class="button" type="primary" @click="login">登录</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link :underline="false" type="info" @click="switchForm">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
    url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>