import { defineStore } from 'pinia'
import axios from 'axios' // 使用默认的axios实例

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    isAuthenticated: false
  }),
  actions: {
    async login(credentials) {
      try {
        const response = await axios.post('/api/auth/login', credentials)
        this.setAuth(response.data)
        return response
      } catch (error) {
        this.clearAuth()
        throw error
      }
    },

    async register(userData) {
      try {
        const response = await axios.post('/api/auth/register', userData)
        return response
      } catch (error) {
        throw error
      }
    },

    setAuth(authData) {
      console.log(authData)
      this.token = authData
      this.isAuthenticated = true
      // 存储到localStorage
      localStorage.setItem('auth', JSON.stringify(authData))
    },

    clearAuth() {
      this.token = null
      this.isAuthenticated = false
      localStorage.removeItem('auth')
      delete axios.defaults.headers.common['Authorization']
    },

    initializeAuth() {
      // 从localStorage初始化状态
      const authData = JSON.parse(localStorage.getItem('auth'))
      if (authData && authData.token) {
        this.authData = authData
      }
    }
  },
  persist: true // 使用pinia持久化插件
})