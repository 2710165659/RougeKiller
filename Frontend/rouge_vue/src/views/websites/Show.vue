<template>
  <div class="container">
    <div class="row">
      <show-number class="row-item" title="网站数量" :num="websitesStore.websiteNum" color="red" :icon="Monitor" />
      <show-number class="row-item" title="IP数量" :num="websitesStore.ipNum" color="#8334F0" :icon="Connection" />
      <show-number class="row-item" title="公司数量" :num="websitesStore.companyNum" color="#EFB111"
        :icon="OfficeBuilding" />
      <show-number class="row-item" title="人员数量" :num="websitesStore.personNum" color="green" :icon="User" />
    </div>
    <div class="row">
      <line-chart class="line-chart" :websiteTrend="websitesStore.websiteTrend" />
      <rates-charts class="rates-charts" :rates="websitesStore.rates" />
    </div>
  </div>
</template>

<script setup>
import { useWebsitesStore } from '@/store/websites'
import ShowNumber from '@/components/websites/ShowNumber.vue'
import { Monitor, Connection, OfficeBuilding, User } from '@element-plus/icons-vue'
import LineChart from '@/components/websites/LineChart.vue'
import RatesCharts from '@/components/websites/RatesCharts.vue'

const websitesStore = useWebsitesStore()

</script>

<style scoped>
.container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 20px;
}

.row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

/* 第一行元素 */
.row-item {
  width: 22%;
  height: 150px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 第二行元素 */
.line-chart {
  width: 60%;
  height: 400px;
}

.rates-charts {
  width: 36%;
  height: 400px;
}
</style>
