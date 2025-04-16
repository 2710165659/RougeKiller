import { defineStore } from "pinia"
import http from "@/axios"
import { reactive, ref } from "vue"

export const useReviewTask = defineStore("reviewTask", {
  state: () => ({
        data: [],
        loading: false,
        hasMore: true,
        params: {
            url: "",
            status: "",
            page: 1,
            size: 10,
        }
  }),
  actions: {
      async searchTasks(append = false) {
          console.log(append);
          
            //append = true即为追加数据  
            //追加数据时，如果正在加载数据或者没有更多数据，则不执行请求
            if (append && (this.loading || !this.hasMore)) return
            //如果不是追加数据，则重置页码为1
            if (!append) this.params.page = 1
            this.loading = true
            try {
                const response = await http.get("/tasks", {
                    params: this.params
                })
                if (!append) {
                    this.data = response.data
                } else {
                    this.data = [...this.data, ...response.data]
                }
                this.hasMore = response.data.length >= this.params.size
                if (this.hasMore) this.params.page++
            } finally {
                this.loading = false
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