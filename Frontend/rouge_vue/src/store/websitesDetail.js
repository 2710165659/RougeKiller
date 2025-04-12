import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesDetailStore = defineStore("websitesDetailSearch", {
  state: () => {
    return {
      loading: false,
      detail: {
        isMalicious: false,
        url: 'www.google.com',
        title: 'Google',
        result: '正常网站',
        ip: '192.168.1.1',
        port: '80',
      },
      ownerNature: '企业',
      owner: {
        name: '河南卓昊科技有限公司',
        companyType: '有限责任公司',
        socialCreditCode: '91410100MA3X1A4KXQ',
        legalRepresentative: '张三',
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