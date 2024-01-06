import {createRouter, createWebHistory} from 'vue-router'
import {useTokenStore} from '@/stores/token'
import {ElMessage} from "element-plus";
// 在component处动态导入组件


// 定义路由关系
const routes = [
    {
        path: '/',
        redirect: '/article/category',
        component: () => import("@/views/Layout.vue"),
        children: [
            {
                path: 'article/',
                redirect: '/article/category',
                children: [
                    {
                        name: 'articleCategory',
                        path: 'category',
                        component: () => import("@/views/article/ArticleCategory.vue"),
                    },
                    {
                        name: 'articleManage',
                        path: 'manage',
                        component: () => import("@/views/article/ArticleManage.vue"),
                    }
                ]
            },
            {
                path: 'user/',
                redirect: '/user/info',
                children: [
                    {
                        name: 'userInfo',
                        path: 'info',
                        component: () => import("@/views/user/UserInfo.vue"),
                    },
                    {
                        name: 'userAvatar',
                        path: 'avatar',
                        component: () => import("@/views/user/UserAvatar.vue"),
                    },
                    {
                        name: 'userResetPsd',
                        path: 'resetPsd',
                        component: () => import("@/views/user/UserResetPassword.vue"),
                    }
                ]
            },
        ]
    },
    {
        path: '/login',
        component: () => import("@/views/Login.vue"),
    }]


// 创建路由
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})


router.beforeEach((to, from, next) => {
    const tokenStore = useTokenStore();
    // 直接放行登录和注册页
    if (to.path === '/login' || to.path === '/register') return next()
    // 判断是否登录含有token，有：放行，没有：跳转到登录页
    if (tokenStore.token !== '' && tokenStore.token !== null) {
        next()
    } else {
        ElMessage.error('请先登录')
        return router.push('/login')
    }

})

// 导出路由
export default router