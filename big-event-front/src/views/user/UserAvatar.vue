<script setup>
import {Plus, Upload} from '@element-plus/icons-vue'
import {ref} from 'vue'
import {useUserInfoStore} from "@/stores/userinfo.js";
import {useTokenStore} from "@/stores/token.js";
import user from "@/api/user";
import {ElMessage} from "element-plus";

const uploadRef = ref()

const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()


//用户头像地址
const imgUrl = ref(userInfoStore.info.userPic)

const handleSuccess = (res) => {
  //返回的是一个数组，数组中包含所有上传的图片对象，对象包含一个键（源文件名称）和一个值（上传后的文件url）
  const imgObj = res.data[0]
  imgUrl.value = imgObj[Object.keys(imgObj)[0]]
}

const updateAvatar = () => {
  user.updateAvatarService({id: userInfoStore.info.id, avatar: imgUrl.value}, res => {
    // 更新pinia存储的用户信息
    userInfoStore.info.userPic = imgUrl.value
    ElMessage.success('头像更换成功')
  }, err => {
    console.log(err)
  })
}


</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>更换头像</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-upload
            ref="uploadRef"
            :auto-upload="true"
            :headers="{'Authorization': tokenStore.token}"
            :on-success="handleSuccess"
            :show-file-list="false"
            action="/api/file/uploadToQiniu"
            class="avatar-uploader"
            name="file"
        >
          <el-image v-if="imgUrl" :src="imgUrl" circle class="avatar"/>
          <el-image v-else :src="userInfoStore.defaultAvatar" circle width="278"/>
        </el-upload>
        <br/>
        <el-button :icon="Plus" size="large" type="primary" @click="uploadRef.$el.querySelector('input').click()">
          选择图片
        </el-button>
        <el-button :icon="Upload" size="large" type="success" @click="updateAvatar">
          上传头像
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      transition: var(--el-transition-duration-fast);
      box-shadow: 5px 5px 0 2px #262222 inset;
      border: 3px dashed var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>