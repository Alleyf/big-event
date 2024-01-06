<script setup>
import {onMounted, ref} from 'vue'
import {useUserInfoStore} from "@/stores/userinfo.js";
import {useTokenStore} from "@/stores/token.js";
import user from '@/api/user'
import {ElMessage} from 'element-plus'

const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()
const userInfo = ref({
  userId: userInfoStore.info.id,
  oldPwd: '',
  newPwd: '',
  rePwd: '',
})


const infoRef = ref()

const rePwdValidator = (rule, value, callback) => {
  if (value == null || value === '') {
    return callback(new Error('请再次确认密码'))
  }
  if (userInfo.value.newPwd !== value) {
    return callback(new Error('两次输入密码不一致'))
  }
  callback()
}

const rules = {
  oldPwd: [
    {required: true, message: '请输入原始密码', trigger: 'blur'},
    {
      pattern: /^\S{4,20}$/,
      message: '密码必须是4-20位的非空字符串',
      trigger: 'blur'
    }
  ],
  newPwd: [
    {required: true, message: '请输入新密码', trigger: 'blur'},
    {
      pattern: /^\S{4,20}$/,
      message: '密码必须是4-20位的非空字符串',
      trigger: 'blur'
    }
  ],
  rePwd: [
    {required: true, message: '请输入重复新密码', trigger: 'blur'},
    {
      validator: rePwdValidator,
      trigger: 'blur'
    }
  ]
}

const clearForm = () => {
  userInfo.value = {
    userId: userInfoStore.info.id,
    oldPwd: '',
    newPwd: '',
    rePwd: '',
  }
}

const updatePassword = () => {
  infoRef.value.validate((valid) => {
    if (valid) {
      user.updatePasswordService(userInfo.value, (res) => {
        ElMessage.success('密码修改成功')
        tokenStore.delToken()
        clearForm()
      }, (err) => {
        console.log(err)
      })
    }
    return false
  })
}

onMounted(() => {
  clearForm()
})


</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>重置密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form ref="infoRef" :model="userInfo" :rules="rules" label-width="100px" size="large">
          <el-form-item label="原始密码" prop="oldPwd">
            <el-input v-model="userInfo.oldPwd" :show-password="true" clearable type="password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input v-model="userInfo.newPwd" :show-password="true" clearable type="password"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" prop="rePwd">
            <el-input v-model="userInfo.rePwd" :show-password="true" clearable type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePassword">提交修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>