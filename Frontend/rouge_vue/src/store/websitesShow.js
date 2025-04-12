import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesShowStore = defineStore("websitesShow", {
  state: () => {
    return {
      loading: false,
      // 第一行信息
      websiteNum: 4459,
      ipNum: 2207,
      companyNum: 1942,
      personNum: 1690,
      // 第二行信息
      websiteTrend: {
        years: [2017, 2018, 2019, 2020, 2021, 2022, 2023],
        total: [1000, 1800, 2800, 3500, 4500, 5800, 6499],
        normal: [800, 1600, 2400, 3200, 4000, 4800, 3499],
        malicious: [100, 200, 300, 400, 450, 500, 506]
      },
      rates: {
        maliciousWebNum: 959,
        normalWebNum: 3499,
        maliciousIpNum: 506,
        normalIpNum: 1701
      },
      // 第三行信息
      webArea: {
        area: ['河北', '北京', '上海', '广东', '浙江', '江苏', '四川', '湖北', '山东', '福建'],
        normalNum: [245, 312, 289, 425, 356, 278, 198, 267, 345, 234],
        maliciousNum: [45, 67, 52, 88, 73, 58, 42, 51, 64, 48]
      }
    }
  },
  actions: {
    async getData() {
      // this.loading = true
      // const res1 = await http.get("/websites/base")
      // const res2 = await http.get("/websites/other")
      // this.loadBaseInfo(res1.data)
      // this.loadOtherInfo(res2.data)
      // this.loading = false
    },
    loadBaseInfo(data) {
      this.websiteNum = data.websiteCount
      this.ipNum = data.ipCount
      this.companyNum = data.companyCount
      this.personNum = data.personCount
    },
    loadOtherInfo(data) {
      this.rates.maliciousWebNum = data.maliciousWebsiteCount
      this.rates.normalWebNum = data.normalWebsiteCount
      this.rates.maliciousIpNum = data.maliciousIpCount
      this.rates.normalIpNum = data.normalIpCount

      const sortedData = [...data.yearlyWebsiteInfo].sort((a, b) => a.year - b.year).slice(-10)
      this.websiteTrend = {
        years: sortedData.map(item => item.year),
        total: sortedData.map(item => item.totalWebsiteCount),
        normal: sortedData.map(item => item.normalWebsiteCount),
        malicious: sortedData.map(item => item.maliciousWebsiteCount)
      }

      this.webArea = {
        area: data.areaWebsiteInfo.map(item => item.provinceName),
        normalNum: data.areaWebsiteInfo.map(item => item.normalCount),
        maliciousNum: data.areaWebsiteInfo.map(item => item.maliciousCount)
      }
    }
  }
})