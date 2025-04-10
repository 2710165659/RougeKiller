import { defineStore } from "pinia"
import http from "@/axios"
import { reactive, ref } from "vue"

export const useWebsitesSearchStore = defineStore("websitesSearch", {
  state: () => ({
    websiteList: [],
    searchForm: reactive({
      title: '',
      url: '',
      company: '',
      pageSize: 6,
      pageNum: 1
    }),
    total: 0,
    maliciousCount: 0,
    normalCount: 0
  }),
  actions: {
    async searchWebsites() {
      this.websiteList = []
      // console.log('请求参数:', this.searchForm)
      const response = await http.get("/websites/search", {
        params: this.searchForm
      })
      // console.log('响应数据:', response.data)
      this.websiteList = response.data.data || []
      this.total = response.data.total || 0
      this.maliciousCount = response.data.maliciousCount || 0
      this.normalCount = response.data.normalCount || 0
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