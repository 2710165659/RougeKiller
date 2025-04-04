import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: null,
    isAuthenticated: false
  }),
  actions: {
    async login(credentials) {
      try {
        const response = await axios.post('/auth/login', credentials)
        this.setAuth(response.data)
        return response
      } catch (error) {
        this.clearAuth()
        throw error
      }
    },

    async register(userData) {
      try {
        const response = await axios.post('/auth/register', userData)
        return response
      } catch (error) {
        throw error
      }
    },

    setAuth(authData) {
      this.user = authData.user
      this.token = authData.token
      this.isAuthenticated = true
      // 存储到localStorage
      localStorage.setItem('auth', JSON.stringify(authData))
      // 设置axios默认授权头
      axios.defaults.headers.common['Authorization'] = `Bearer ${authData.token}`
    },

    clearAuth() {
      this.user = null
      this.token = null
      this.isAuthenticated = false
      localStorage.removeItem('auth')
      delete axios.defaults.headers.common['Authorization']
    },

    initializeAuth() {
      // 从localStorage初始化状态
      const authData = JSON.parse(localStorage.getItem('auth'))
      if (authData && authData.token) {
        this.setAuth(authData)
      }
    }
  },
  persist: true // 使用pinia持久化插件
})