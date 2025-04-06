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
      this.loading = true
      const res1 = await http.get("/websites/base")
      const res2 = await http.get("/websites/other")
      this.loadBaseInfo(res1.data)
      this.loadOtherInfo(res2.data)
      this.loading = false
    },
    loadBaseInfo(data) {
      this.websiteNum = data.websiteCount
      this.ipNum = data.ipCount
      this.companyNum = data.companyCount
      this.personNum = data.personCount
    },
    loadOtherInfo(data) {
      // {
      //   "normalWebsiteCount": 49,
      //     "normalIpCount": 35,
      //       "maliciousWebsiteCount": 21,
      //         "maliciousIpCount": 15,
      //           "yearlyWebsiteInfo": [
      //             {
      //               "year": 2025,
      //               "totalWebsiteCount": 70,
      //               "normalWebsiteCount": 49,
      //               "maliciousWebsiteCount": 21
      //             }
      //           ],
      //             "areaWebsiteInfo": [
      //               {
      //                 "provinceName": "上海",
      //                 "normalCount": 2,
      //                 "maliciousCount": 2
      //               },
      //               {
      //                 "provinceName": "广东",
      //                 "normalCount": 2,
      //                 "maliciousCount": 2
      //               },
      //               {
      //                 "provinceName": "江苏",
      //                 "normalCount": 2,
      //                 "maliciousCount": 2
      //               },
      //               {
      //                 "provinceName": "山东",
      //                 "normalCount": 2,
      //                 "maliciousCount": 2
      //               },
      //               {
      //                 "provinceName": "湖北",
      //                 "normalCount": 2,
      //                 "maliciousCount": 2
      //               },
      //               {
      //                 "provinceName": "湖南",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "海南",
      //                 "normalCount": 1,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "广西",
      //                 "normalCount": 1,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "河南",
      //                 "normalCount": 1,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "天津",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "山西",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "浙江",
      //                 "normalCount": 4,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "重庆",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "安徽",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "福建",
      //                 "normalCount": 3,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "北京",
      //                 "normalCount": 4,
      //                 "maliciousCount": 1
      //               },
      //               {
      //                 "provinceName": "四川",
      //                 "normalCount": 4,
      //                 "maliciousCount": 0
      //               },
      //               {
      //                 "provinceName": "河北",
      //                 "normalCount": 2,
      //                 "maliciousCount": 0
      //               },
      //               {
      //                 "provinceName": "贵州",
      //                 "normalCount": 2,
      //                 "maliciousCount": 0
      //               },
      //               {
      //                 "provinceName": "黑龙江",
      //                 "normalCount": 2,
      //                 "maliciousCount": 0
      //               }
      //             ]
      // }
      console.log(data)
    }
  }
})