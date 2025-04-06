import { defineStore } from "pinia"
import http from "@/axios"
import { reactive, ref } from "vue"

export const useWebsitesSearchStore = defineStore("websitesSearch", {
  state: () => ({
    loading: false,
    websiteList: [],
    searchForm: reactive({
      title: '',
      url: '',
      company: '',
      pageSize: 6,
      pageNum: 1
    }),
    total: 0
  }),
  actions: {
    async searchWebsites() {
        this.loading = true
        this.websiteList = []
        console.log('请求参数:', this.searchForm) // 调试日志
      try {
        const response = await http.get("/websites/search", {
          params: this.searchForm
        })
        console.log('响应数据:', response.data) // 调试日志
        this.websiteList = response.data.data || []
        this.total = response.data.total || 0
      } catch (error) {
        console.error('请求错误:', error) // 错误日志
      } finally {
        this.loading = false
      }
    },
    
    resetSearch() {
      this.searchForm = reactive({
        title: '',
        url: '',
        company: '',
        pageSize: 6,
        pageNum: 1
      })
        this.websiteList = []
    }
  }
})