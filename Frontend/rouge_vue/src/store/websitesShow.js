import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesShowStore = defineStore("websitesShow", {
  state: () => {
    return {
      loading: true,
      // 第一行信息
      websiteNum: 4459,
      ipNum: 2207,
      companyNum: 1942,
      personNum: 1690,
      // 第二行信息
      websiteTrend: {
        years: [2017, 2018, 2019, 2020, 2021, 2022, 2023],
        totalWebNum: [1000, 2000, 3000, 4000, 5000, 6000, 7000],
        maliciousWebNum: [200, 400, 600, 800, 1000, 1200, 959],
        normalWebNum: [800, 1600, 2400, 3200, 4000, 4800, 3499],
        maliciousIpNum: [100, 200, 300, 400, 450, 500, 506],
        normalIpNum: [400, 800, 1200, 1600, 1650, 1700, 1701]
      },
      rates: {
        maliciousWebNum: 959,
        normalWebNum: 3499,
        maliciousIpNum: 506,
        normalIpNum: 1701,
      }
    }
  },
})