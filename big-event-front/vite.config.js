import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        proxy: {
            // 配置跨域，获取请求路径中包含了api的接口
            '/api': {
                target: 'http://localhost:8080', // 后端接口源，代理目标的基础路径
                changeOrigin: true, //修改源
                // rewrite: (path) => path.replace(/^\/api/, '') //后端自带了api路径，无需重写
            }
        }
    }
})
