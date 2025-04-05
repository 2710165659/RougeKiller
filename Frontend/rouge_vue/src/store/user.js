import { defineStore } from "pinia"
import http from "@/axios"

export const useUserStore = defineStore("user", {
  state: () => {
    return {
      loading: true,
      user: {
        id: null,
        name: '',
        email: '',
        saltedPassword: '',
        role: '',
        createdAt: null,
        updatedAt: null
      }
    }
  },
  actions: {
    async fetchUser() {
      try {
        const response = await http.get("/user/info")
        this.user = response.data
        this.loading = false
      } catch (error) {
        return error
      }
    }
  }
})