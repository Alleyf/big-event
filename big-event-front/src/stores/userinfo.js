import {defineStore} from "pinia";
import {ref} from "vue";


export const useUserInfoStore = defineStore('userInfo', () => {

    const info = ref({})
    const defaultAvatar = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")


    const setInfo = (newInfo) => {
        info.value = newInfo;
    }

    const removeInfo = () => {
        info.value = {}
    }

    return {
        info,
        defaultAvatar,
        setInfo,
        removeInfo
    }
}, {
    persist: true
})