import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesDetailStore = defineStore("websitesDetailSearch", {
  state: () => {
    return {
      loading: false,
      detail: {
        isMalicious: false,
        url: ' https://www.32r.com',
        title: '32软件站',
        result: '正常网站',
        ip: '218.24.86.80',
        port: '443',
      },
      ownerNature: '企业',
      owner: {
        name: '万海林',
        companyType: '个人',
        socialCreditCode: '',
        legalRepresentative: '万海林',
        establishMentDate: '2022-04-15',
        approvalDate: '',
        registeredCapital: '',
        businessScope: '',
        registrationNumber: '',
        address: '',
        businessTermStart: '赣ICP备20001389号',
        businessTermEnd: '赣ICP备20001389号-1',
      }



    }
  },
  actions: {


  }

})