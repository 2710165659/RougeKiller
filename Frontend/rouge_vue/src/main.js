import { createApp } from 'vue'
import App from './App.vue'
// Element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// vue-router
import router from './router'
// pinia状态管理
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'
const pinia = createPinia()
pinia.use(createPersistedState())


const app = createApp(App)

// 插件
app.use(ElementPlus)
app.use(router)
app.use(pinia)

app.mount('#app')
