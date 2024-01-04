<script setup>
import {
  CaretBottom,
  Crop,
  EditPen,
  Management,
  Promotion,
  SwitchButton,
  User,
  UserFilled
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import {useRouter} from "vue-router";
import {useTokenStore} from "@/stores/token.js";

// 路由
const router = useRouter();
// 状态
const tokenStore = useTokenStore();

const logout = () => {
  tokenStore.delToken();
  router.push('/login');
}
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <!--      菜单栏-->
      <el-menu active-text-color="#ffd04b" background-color="#232323" router
               text-color="#fff">
        <el-menu-item index="/article/category">
          <el-icon>
            <Management/>
          </el-icon>
          <span>文章分类</span>
        </el-menu-item>
        <el-menu-item index="/article/manage">
          <el-icon>
            <Promotion/>
          </el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-sub-menu index="/user">
          <template #title>
            <el-icon>
              <UserFilled/>
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User/>
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPsd">
            <el-icon>
              <EditPen/>
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>黑白程序员：<strong>小范</strong></div>
        <el-dropdown placement="bottom-end">
                    <span class="el-dropdown__box">
                        <el-avatar :src="avatar"/>
                        <el-icon>
                            <CaretBottom/>
                        </el-icon>
                    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item :icon="User" command="profile">基本资料</el-dropdown-item>
              <el-dropdown-item :icon="Crop" command="avatar">更换头像</el-dropdown-item>
              <el-dropdown-item :icon="EditPen" command="password">重置密码</el-dropdown-item>
              <el-dropdown-item :icon="SwitchButton" command="logout" @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <!--        <div style="width: 1290px; height: 570px;border: 1px solid red;">-->
        <div style="width: 100%; height: fit-content;">
          <router-view/>
        </div>
      </el-main>
      <!-- 底部区域 -->
      <el-footer>大事件 ©2023 Created by 黑白程序员</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
  //width: fit-content;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>