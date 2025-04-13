import { defineStore } from "pinia"
import http from "@/axios"
import { reactive, ref } from "vue"

export const useReviewTask = defineStore("reviewTask", {
  state: () => ({
    data: [],
    url: "",
    status:"",
  }),
  actions: {
    async searchTasks() {
        this.data = []
        const response = await http.get("/tasks", {
            params: { url:this.url }
        })
        console.log(response);
        this.data = response.data
            if (this.status != "") {
                this.data = this.data.filter(task => {
                    return task.status && task.status.trim() === this.status.trim()
                })
            }
          
      },
    async startTask(id) {
        const response = await http.post("/tasks/start", { id })
        this.searchTasks()
    },
    async deleteTask(id) {
        const response = await http.delete("tasks/" + id)
        this.searchTasks()
    },
    resetSearch() {
        this.url = ""
        this.status = ""
        this.data = []
        this.searchTasks()
    }
  }
})