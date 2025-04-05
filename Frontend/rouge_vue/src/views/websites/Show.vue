<template>
  <div class="container">
    <div class="line">
      <show-number class="line-item" title="网站数量" :num="websitesStore.websiteNum" color="red" :icon="Monitor" />
      <show-number class="line-item" title="IP数量" :num="websitesStore.ipNum" color="purple" :icon="Connection" />
      <show-number class="line-item" title="公司数量" :num="websitesStore.companyNum" color="yellow"
        :icon="OfficeBuilding" />
      <show-number class="line-item" title="人员数量" :num="websitesStore.personNum" color="green" :icon="User" />
    </div>
  </div>
</template>

<script setup>
import ShowNumber from '@/components/websites/ShowNumber.vue'
import { Monitor, Connection, OfficeBuilding, User } from '@element-plus/icons-vue'
import { useWebsitesStore } from '@/store/websites'

const websitesStore = useWebsitesStore()

</script>

<style scoped>
.container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: start;
}

.line {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.line-item {
  width: 22%;
  height: 150px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
