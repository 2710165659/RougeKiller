import axios from 'axios'
import { useAuthStore } from './store/auth'
import router from './router'

const http = axios.create({
  baseURL: '/api'
})

// 请求拦截器：添加token
http.interceptors.request.use(config => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
}, error => {
  return Promise.reject(error)
})

// 响应拦截器：处理token失效和数据格式
http.interceptors.response.use(response => {
  // 如果响应数据有body字段，则直接返回body内容
  if (response.data && response.data.body) {
    return {
      ...response,
      data: response.data.body
    }
  }
  return response
}, error => {
  if (error.response.status === 401) {
    const authStore = useAuthStore()
    authStore.clearAuth()
    // 跳转到登录页
    router.push('/login')
  }
  return Promise.reject(error)
})

export default http