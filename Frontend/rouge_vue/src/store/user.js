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
      this.loading = true
      try {
        const response = await http.get("/user/info")
        this.user = response.data
      } finally {
        this.loading = false
      }
    },

    async updateUser(user) {
      await http.put("/user/update", user)
      await this.fetchUser()
    },

    async deleteUser() {
      await http.delete("/user/delete")
    }
  }
})