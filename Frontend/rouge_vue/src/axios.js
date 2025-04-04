import axios from 'axios'
import { useAuthStore } from './store/auth'

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

// 响应拦截器：处理token失效
http.interceptors.response.use(response => {
  return response
}, error => {
  if (error.response.status === 401) {
    const authStore = useAuthStore()
    authStore.clearAuth()
    // 跳转到登录页
    window.location.href = '/login'
  }
  return Promise.reject(error)
})

export default http