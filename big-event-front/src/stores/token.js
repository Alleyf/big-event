//导入pinia
import {defineStore} from "pinia";
import {ref} from "vue";


/**
 * 定义状态
 * 第一个参数：名字，唯一性
 * 第二个参数：函数，函数的内部可以定义状态的所有内容
 * 返回值：函数
 * @type {StoreDefinition<"token", {token: string}, {}, {setToken(*): void}>}
 */
// export const useTokenStore = defineStore('token', {
//     // 定义状态,可以设置默认初值
//     state: () => ({token: ''}),
//     // 定义对状态的操作（修改，删除，获取···）
//     actions: {
//         setToken(token) {
//             this.token = token
//         },
//         delToken() {
//             this.token = ''
//         }
//     }
// })

export const useTokenStore = defineStore('token', () => {
        // 1.定义状态,可以设置默认初值
        const token = ref('')
        // 2.定义对状态的操作（修改，删除，获取···）
        // 2.1修改token
        const setToken = (newToken) => {
            token.value = newToken
        }
        // 2.2删除token
        const delToken = () => {
            token.value = ''
        }
        return {
            token, setToken, delToken
        }
    },
    {
        persist: true
    }
)