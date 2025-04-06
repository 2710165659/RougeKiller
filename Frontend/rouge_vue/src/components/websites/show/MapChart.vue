<template>
  <div class="row-container">
    <h1>分布图</h1>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, watchEffect, ref } from 'vue';


const props = defineProps(['webArea'])
const chinaGeoJson = require('@/assets/china-geo.json')
const data = computed(() => { return props.webArea })

const chartRef = ref(null)

watchEffect(() => {
  if (!chartRef.value) return

  echarts.registerMap('china', chinaGeoJson)

  const chart = echarts.init(chartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      backgroundColor: '#252B3366',
      borderColor: '#333',
      textStyle: {
        color: '#fff'
      },
      formatter: params => {
        return `${params.name}<br/>
        正常网站: ${data.value.normalNum[data.value.area.indexOf(params.name)] || 0}<br/>
        恶意网站: ${data.value.maliciousNum[data.value.area.indexOf(params.name)] || 0}`
      }
    },
    visualMap: {
      min: 0,
      max: Math.max(...data.value.area.map((_, i) =>
        data.value.normalNum[i] + data.value.maliciousNum[i])) + 10,
      text: ['数量'],
      realtime: false,
      calculable: true,
      inRange: {
        color: ['#5470C6', '#91CC75', '#FF6030']
      },
      textStyle: {
        color: '#fff'
      }
    },
    series: [{
      type: 'map',
      map: 'china',
      roam: true,
      zoom: 2,
      top: '30%',
      itemStyle: {
        areaColor: '#5470C6',
        borderColor: 'whitesmoke',
        borderWidth: 1
      },
      emphasis: {
        label: {
          show: true,
          color: 'whitesmoke'
        }
      },
      data: data.value.area.map((name, index) => {
        return {
          name: name,
          value: data.value.normalNum[index] + data.value.maliciousNum[index]
        };
      })
    }]
  }

  chart.setOption(option)

  const handleResize = () => chart.resize()
  window.addEventListener('resize', handleResize)

  return () => {
    window.removeEventListener('resize', handleResize)
    chart.dispose()
  }
})

</script>

<style scoped>
.row-container {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
  background-color: #343A40;
}

h1 {
  position: absolute;
  color: whitesmoke;
  font-size: 20px;
  height: 10%;
  top: 3%;
  left: 3%;
}

.chart {
  position: absolute;
  width: 100%;
  height: 87%;
  top: 13%;
}
</style>
