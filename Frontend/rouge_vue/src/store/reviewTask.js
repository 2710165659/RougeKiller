import { defineStore } from "pinia"
import http from "@/axios"
import { reactive, ref } from "vue"

export const useReviewTask = defineStore("reviewTask", {
  state: () => ({
        data: [],
        params: {
            url: "",
            status: "",
            page: 1,
            size: 10,
        }
  }),
  actions: {
    async searchTasks() {
        this.data = []
        const response = await http.get("/tasks", {
            params: this.params
        })
        this.data = response.data          
      },
    async startTask(id) {
        const response = await http.post("/tasks/start", { id })
        this.searchTasks()
    },
    async deleteTask(id) {
        const response = await http.delete("tasks/" + id)
        this.searchTasks()
    },
    async addTask(url) {
        const response = await http.post("/tasks", { fullUrl: url })
        this.resetSearch()
    },
    resetSearch() {
        this.params.url = ""
        this.params.status = ""
        this.params.page = 1
        this.params.size = 10
        this.searchTasks()
    }
  }
})