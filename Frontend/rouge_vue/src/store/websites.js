import { defineStore } from "pinia"
import http from "@/axios"

export const useWebsitesStore = defineStore("websites", {
  state: () => {
    return {
      loading: true,
      websiteNum: 4459,
      ipNum: 2207,
      companyNum: 1942,
      personNum: 1690,

    }
  },
})