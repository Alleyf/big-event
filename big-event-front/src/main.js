import './assets/main.scss'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {createPersistedState} from "pinia-persistedstate-plugin";

import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import router from '@/router'
import App from './App.vue'
import {createPinia} from "pinia";

const persist = createPersistedState()
const app = createApp(App)
const pinia = createPinia();
pinia.use(persist)
app.use(pinia)
app.use(ElementPlus, {locale: zhCn})
app.use(router)
app.mount('#app')
