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
        totalNum: [50, 300, 500, 700, 1000, 2000, 3000],
        maliciousNum: [10, 30, 100, 150, 200, 300, 500],
        normalNum: [40, 270, 600, 850, 800, 1700, 2500]
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