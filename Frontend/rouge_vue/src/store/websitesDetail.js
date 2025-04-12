import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesDetailStore = defineStore("websitesDetailSearch", {
  state: () => {
    return {
      loading: false,
      detail: {
        isMalicious: true,
        url: 'http://app.diexun.com:8180',
        title: '蝶讯网客户端下载 - DICTION全球时尚趋势资讯提供商',
        result: '恶意网站',
        ip: '111.20.14.170',
        port: '8081',
      },
      ownerNature: '企业',
      owner: {
        name: '河南卓昊科技有限公司',
        companyType: '有限责任公司',
        socialCreditCode: '91410100MA3X1A4KXQ',
        legalRepresentative: '张昊卓',
        establishMentDate: '2020-01-01',
        approvalDate: '2020-01-06',
        registeredCapital: '1000000',
        businessScope: '计算机软件开发',
        registrationNumber: '410105000000000',
        address: '河南省郑州市金水区',
        businessTermStart: '2020-01-01',
        businessTermEnd: '2025-01-06',
      }
      // 关联图谱数据

    }
  },
  actions: {

  }

})