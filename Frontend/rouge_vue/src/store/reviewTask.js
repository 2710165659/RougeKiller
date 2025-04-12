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
                console.log('Filtering with status:', this.status)
                this.data = this.data.filter(task => {
                    console.log('Comparing task status:', task.status, 'with filter:', this.status)
                    return task.status && task.status.trim() === this.status.trim()
                })
                console.log('Filtered results:', this.data)
            }
          
    },

    resetSearch() {
        this.url = ""
        this.status = ""
        this.data = []
        this.searchTasks()
    }
  }
})