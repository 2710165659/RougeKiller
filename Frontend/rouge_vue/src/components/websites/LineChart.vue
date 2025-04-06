<template>
  <div class="row-container">
    <h1>网站趋势变化图</h1>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, watchEffect, ref } from 'vue';


const props = defineProps(['websiteTrend'])
const data = computed(() => { return props.websiteTrend })

const chartRef = ref(null)

watchEffect(() => {
  if (!chartRef.value) return

  const chart = echarts.init(chartRef.value)
  const option = {

    tooltip: {
      trigger: 'axis',
      backgroundColor: '#252B3366',
      borderColor: '#333',
      textStyle: {
        color: '#fff'
      }
    },
    legend: {
      left: '8%',
      textStyle: {
        color: '#fff'
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: data.value.years
    },
    yAxis: {
      type: 'value',
      splitLine: {
        lineStyle: {
          opacity: 0.7
        }
      }
    },
    series: [
      {
        name: '全部网站',
        data: data.value.totalWebNum,
        type: 'line',
        areaStyle: {
          opacity: 0.5
        },
        smooth: true
      },
      {
        name: '恶意网站',
        data: data.value.maliciousWebNum,
        type: 'line',
        areaStyle: {
          opacity: 0.5
        },
        smooth: true
      },
      {
        name: '正常网站',
        data: data.value.normalWebNum,
        type: 'line',
        areaStyle: {
          opacity: 0.5
        },
        smooth: true
      },
      {
        name: '恶意IP',
        data: data.value.maliciousIpNum,
        type: 'line',
        areaStyle: {
          opacity: 0.5
        },
        smooth: true
      },
      {
        name: '正常IP',
        data: data.value.normalIpNum,
        type: 'line',
        areaStyle: {
          opacity: 0.5
        },
        smooth: true
      }
    ]
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
  height: 90%;
  top: 13%;
}
</style>
