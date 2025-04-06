<template>
  <div class="row-container">
    <h1>网站-地区堆积图</h1>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, watchEffect, ref } from 'vue';


const props = defineProps(['webArea'])
const data = computed(() => {
  if (!props.webArea) return { area: [], normalNum: [], maliciousNum: [] }
  return {
    area: props.webArea.area.slice(0, 10),
    normalNum: props.webArea.normalNum.slice(0, 10),
    maliciousNum: props.webArea.maliciousNum.slice(0, 10)
  }
})

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
    xAxis: [
      {
        type: 'category',
        data: data.value.area
      }
    ],
    yAxis: [
      {
        type: 'value',
        splitLine: {
          lineStyle: {
            opacity: 0.7
          }
        }
      }
    ],
    series: [
      {
        name: '正常网站数量',
        type: 'bar',
        stack: 'total',
        data: data.value.normalNum
      },
      {
        name: '恶意网站数量',
        type: 'bar',
        stack: 'total',
        data: data.value.maliciousNum
      }
    ]
  };

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
  user-select: none;
}

.chart {
  position: absolute;
  width: 100%;
  height: 90%;
  top: 13%;
}
</style>
