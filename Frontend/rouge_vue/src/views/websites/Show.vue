<template>
  <div class="container">
    <div class="row">
      <show-number class="row-item" title="网站数量" :num="websitesShowStore.websiteNum" color="red" :icon="Monitor" />
      <show-number class="row-item" title="IP数量" :num="websitesShowStore.ipNum" color="#8334F0" :icon="Connection" />
      <show-number class="row-item" title="公司数量" :num="websitesShowStore.companyNum" color="#EFB111"
        :icon="OfficeBuilding" />
      <show-number class="row-item" title="人员数量" :num="websitesShowStore.personNum" color="green" :icon="User" />
    </div>
    <div class="row">
      <line-chart class="line-chart" :websiteTrend="websitesShowStore.websiteTrend" />
      <rates-charts class="rates-charts" :rates="websitesShowStore.rates" />
    </div>
    <div class="row">
      <bar-chart class="bar-chart" :webArea="websitesShowStore.webArea" />
      <map-chart class="map-chart" :webArea="websitesShowStore.webArea" />
    </div>
  </div>
</template>

<script setup>
import { useWebsitesShowStore } from '@/store/websitesShow'
import ShowNumber from '@/components/websites/show/ShowNumber.vue'
import { Monitor, Connection, OfficeBuilding, User } from '@element-plus/icons-vue'
import LineChart from '@/components/websites/show/LineChart.vue'
import RatesCharts from '@/components/websites/show/RatesCharts.vue'
import BarChart from '@/components/websites/show/BarChart.vue'
import MapChart from '@/components/websites/show/MapChart.vue'

const websitesShowStore = useWebsitesShowStore()

</script>

<style scoped>
.container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 20px;
  height: calc(100vh-20px);
  overflow: auto;
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
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.rates-charts {
  width: 38%;
  height: 400px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 第三行元素 */
.bar-chart {
  width: 45%;
  height: 400px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.map-chart {
  width: 53%;
  height: 400px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}


/* 滚动条样式 */
.container::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.container::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.container::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
